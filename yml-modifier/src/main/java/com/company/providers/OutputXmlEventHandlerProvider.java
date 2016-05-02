package com.company.providers;

import com.company.ModifierConfig;
import com.company.handlers.OffersSeparator;
import com.company.handlers.ProgressHandler;
import company.StAXService;
import company.conditions.InElementCondition;
import company.handlers.xml.*;
import company.providers.InputStreamXmlEventReaderProvider;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class OutputXmlEventHandlerProvider {

    ModifierConfig config;
    Supplier<InputStream> inputXmlFileSupplier;

    public OutputXmlEventHandlerProvider(ModifierConfig config, Supplier<InputStream> inputXmlFileSupplier) {
        this.config = config;
        this.inputXmlFileSupplier = inputXmlFileSupplier;
    }

    public XmlEventHandler get() throws XMLStreamException {
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

        handlers.add(new XmlEventFilter(new OffersSeparator( fileXmlEventWriters, offerCount/config.getFilesCount() ), new InElementCondition("offers") ));
        handlers.add(new ProgressHandler(offerCount));

        return new OldResultsCleanerXmlEventHandler(config.getOutputDir()+"/output0.xml", new SuccessiveXmlEventHandler(handlers));
    }

    private int getOfferCount() throws XMLStreamException {
        EventCounter eventCounter = new EventCounter(new InElementCondition("offers").and((event) -> event.isStartElement()
                && event.asStartElement().getName().getLocalPart().equals("offer")));

        new StAXService(new InputStreamXmlEventReaderProvider(inputXmlFileSupplier.get(), config.getEncoding())).process(eventCounter);

        return eventCounter.getCount();
    }
}