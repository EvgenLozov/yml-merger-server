package com.company.factories.handler;

import company.Factory;
import company.StAXService;
import company.providers.XMLEventReaderProvider;
import company.conditions.AfterElement;
import company.conditions.OnceTime;
import company.handlers.xml.SuccessiveXmlEventHandler;
import company.handlers.xml.XmlEventHandler;
import company.handlers.xml.XmlEventHandlingProcessTrigger;

import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * конструює XmlEventHandler призначенням якого являється запуск процесу обробки xml-файлів представлених в
 * readerProviders. Процес обробки запускається один раз після першої зустрічі елементу afterElementName.
 * XmlEventHandler з допомогою якого будуть оброблятись файли надає фабрика handlerFactory
 */
public class ElementWriterHandlerFactory implements Factory<XmlEventHandler>{

    List<? extends XMLEventReaderProvider> readerProviders;
    String afterElementName;
    Factory<XmlEventHandler> handlerFactory;

    public ElementWriterHandlerFactory(List<? extends XMLEventReaderProvider> readerProviders,
                                       String afterElementName,
                                       Factory<XmlEventHandler> handlerFactory) {
        this.readerProviders = readerProviders;
        this.afterElementName = afterElementName;
        this.handlerFactory = handlerFactory;
    }

    @Override
    public XmlEventHandler get() {

        List<XmlEventHandler> handlers = new ArrayList<>();

        for (XMLEventReaderProvider readerProvider : readerProviders) {

            Predicate<XMLEvent> writeElementsConditions = new OnceTime(new AfterElement(afterElementName));
            StAXService service = new StAXService(readerProvider);

            handlers.add(new XmlEventHandlingProcessTrigger(writeElementsConditions, service, handlerFactory.get()));
        }

        return new SuccessiveXmlEventHandler(handlers);
    }

}
