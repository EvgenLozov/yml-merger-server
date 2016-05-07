package com.company;

import com.company.functions.RemoveWordsOperator;
import com.company.handlers.OffersCategoryIdModifier;
import com.company.handlers.OffersSeparator;
import com.company.handlers.ProgressHandler;
import com.company.operators.AddContentToDescription;
import com.company.operators.ModifyOfferDescription;
import company.StAXService;
import company.conditions.EndElement;
import company.conditions.InElementCondition;
import company.conditions.StartElement;
import company.handlers.xml.*;
import company.handlers.xml.buffered.*;
import company.handlers.xml.insert.SimpleXmlEventSupplier;
import company.handlers.xml.insert.XmlEventInserter;
import company.handlers.xml.insert.XmlEventSupplier;
import company.providers.XMLEventReaderProvider;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.function.Predicate;

//import java.nio.charset.Charset;

public class ModifierXmlEventHandlerProvider {

    ModifierConfig config;
    XMLEventReaderProvider readerProvider;

    private XMLEventFactory xmlEventFactory = XMLEventFactory.newFactory();

    public ModifierXmlEventHandlerProvider(ModifierConfig config, XMLEventReaderProvider readerProvider) {
        this.config = config;
        this.readerProvider = readerProvider;
    }

    public XmlEventHandler get() throws FileNotFoundException, UnsupportedEncodingException, XMLStreamException {
        List<XmlEventHandler> handlers = new ArrayList<>();
        if (config.isModifyOfferId())
            handlers.add(new AttributeValueModifier("offer", "id", old -> config.getOfferIdPrefix()+old));

        if (config.isModifyCategoryId()) {
            handlers.add(new AttributeValueModifier("category", "id", (old) -> config.getCategoryIdPrefix() + old));
            handlers.add(new AttributeValueModifier("category", "parentId", (old) -> config.getCategoryIdPrefix() + old));
            handlers.add(new OffersCategoryIdModifier(config.getCategoryIdPrefix()));
        }

        handlers.add(new ModifyElementAttributes("offer", new OfferAttributeModificator(xmlEventFactory) ));
        handlers.add(new XmlEventFilter(new ModifyTextData((old) -> old.equals("0") ? "10000" : old ), new InElementCondition("price")));

        TreeSet<String> forbiddenWords = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        forbiddenWords.add("���");
        forbiddenWords.add("���.");
        forbiddenWords.add("�����");

        handlers.add(new XmlEventFilter(new ModifyTextData(new RemoveWordsOperator(forbiddenWords)), new InElementCondition("description").or(new InElementCondition("name"))));

        handlers.add(getOutputHandler());

        XmlEventHandler handler = new SuccessiveXmlEventHandler(handlers);

        List<BufferXmlEventOperator> operators = new ArrayList<>();

        if (config.isModifyDescription())
            operators.add(provideDescriptionModification());

        operators.add(getOperatorsForDeletedOffers());

        handler = new BufferedXmlEventHandler(handler, new StartElement("offer"), new EndElement("offer"), new ComplexBufferXmlEventOperator(operators) );

        XmlEventSupplier xmlEventSupplier = new SimpleXmlEventSupplier(
                (event)-> event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("shop"),
                () -> Arrays.asList(xmlEventFactory.createEndElement("", "", "offers")));

        handler = new XmlEventInserter(handler, xmlEventSupplier);

        handler = new XmlEventFilter(handler, new StartElement("deleted_offers").or(new EndElement("deleted_offers")).or(new EndElement("offers")).negate());

        return handler;
    }

    private BufferXmlEventOperator getOperatorsForDeletedOffers() throws UnsupportedEncodingException {
        List<BufferXmlEventOperator> operators = new ArrayList<>();

        operators.add(new AddElementIfAbsent("categoryId", xmlEventFactory, Optional.of(config.getRemovedCategoryId())));
        operators.add(new AddElementIfAbsent("currencyId", xmlEventFactory, Optional.of("RUR")));
        operators.add(new AddElementIfAbsent("description", xmlEventFactory, Optional.empty()));

        String removedStr = new String("�������".getBytes(), config.getEncoding());

        operators.add(new AddElementIfAbsent("name", xmlEventFactory, Optional.of(removedStr)));
        operators.add(new AddElementIfAbsent("model", xmlEventFactory, Optional.of(removedStr)));
        operators.add(new AddElementIfAbsent("price", xmlEventFactory, Optional.of("10000")));
        operators.add(new AddElementIfAbsent("url", xmlEventFactory, Optional.of("http://kupi-na-dom.ru")));
        operators.add(new AddElementIfAbsent("vendor", xmlEventFactory, Optional.empty()));

        BufferXmlEventOperator operator = new ComplexBufferXmlEventOperator(operators);

        return new PredicateOperator(operator, (events) -> events.get(0).asStartElement().getAttributeByName(QName.valueOf("available")) == null ||
                !events.get(0).asStartElement().getAttributeByName(QName.valueOf("available")).getValue().equals("true"));

    }

    private XmlEventHandler getOutputHandler() throws XMLStreamException {

        if (config.getLimitSize() > 0) {
            return  new OldResultsCleanerXmlEventHandler(config.getOutputDir()+"/output0.xml",new WriteToLimitSizeFile(config.getOutputDir(), config.getEncoding(), config.getLimitSize()));
        }

        int offerCount = getOfferCount();

        List<XmlEventHandler> fileXmlEventWriters = new ArrayList<>();
        Predicate<XMLEvent> closeCondition = (event) -> event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("yml_catalog");
        for (int i = 0; i < config.getFilesCount(); i++)
            fileXmlEventWriters.add(new WriteEventToFile(config.getOutputDir() + "/output"+i+".xml", config.getEncoding(), closeCondition));

        List<XmlEventHandler> handlers = new ArrayList<>();
        for (XmlEventHandler fileXmlEventWriter : fileXmlEventWriters)
            handlers.add(new XmlEventFilter(fileXmlEventWriter, new InElementCondition("offers").negate()));

        int offersPerFile = offerCount/config.getFilesCount() == 0 ? 1 : offerCount/config.getFilesCount();
        handlers.add(new XmlEventFilter(new OffersSeparator( fileXmlEventWriters, offersPerFile ), new InElementCondition("offers") ));
        handlers.add(new ProgressHandler(offerCount));

        return new OldResultsCleanerXmlEventHandler(config.getOutputDir()+"/output0.xml", new SuccessiveXmlEventHandler(handlers));
    }

    private BufferXmlEventOperator provideDescriptionModification()
    {
        OfferDescriptionProvider provider = new OfferDescriptionProvider(config.getTemplate(),"utf-8");

        List<BufferXmlEventOperator> operators = new ArrayList<>();
        operators.add(new AddElementIfAbsent("description",xmlEventFactory, Optional.<String>empty()));
        operators.add(new ModifyOfferDescription(provider));
        operators.add(new AddContentToDescription(provider, xmlEventFactory.newFactory()));

        return new ComplexBufferXmlEventOperator(operators);
    }

    private int getOfferCount() throws XMLStreamException {
        EventCounter eventCounter = new EventCounter(new InElementCondition("offers").and((event) -> event.isStartElement()
                && event.asStartElement().getName().getLocalPart().equals("offer")));

        new StAXService(readerProvider).process(eventCounter);

        return eventCounter.getCount();
    }



}
