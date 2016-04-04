package com.company.providers;

import com.company.ModifierConfig;
import com.company.OfferAttributeModificator;
import com.company.OfferDescriptionProvider;
import com.company.functions.RemoveWordsOperator;
import com.company.handlers.OffersCategoryIdModifier;
import com.company.handlers.OffersSeparator;
import com.company.handlers.ProgressHandler;
import com.company.operators.event.AddContentToDescription;
import com.company.operators.event.ModifyOfferDescription;
import company.StAXService;
import company.conditions.*;
import company.handlers.xml.*;
import company.handlers.xml.buffered.*;
import company.handlers.xml.insert.SimpleXmlEventSupplier;
import company.handlers.xml.insert.XmlEventInserter;
import company.handlers.xml.insert.XmlEventSupplier;
import company.providers.FileXMLEventReaderProvider;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
//import java.nio.charset.Charset;
import java.util.*;
import java.util.function.Predicate;

public class ModifierXmlEventHandlerProvider {

    ModifierConfig config;

    private XMLEventFactory xmlEventFactory = XMLEventFactory.newFactory();

    public ModifierXmlEventHandlerProvider(ModifierConfig config) {
        this.config = config;
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
        forbiddenWords.add("опт");
        forbiddenWords.add("опт.");
        forbiddenWords.add("оптом");

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

        String removedStr = new String("удалено".getBytes(), config.getEncoding());

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
        return new SplitXmlEventHandlerProvider().get();
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

}
