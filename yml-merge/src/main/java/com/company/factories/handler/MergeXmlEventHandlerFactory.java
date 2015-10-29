package com.company.factories.handler;

import company.Factory;
import company.handlers.xml.SuccessiveXmlEventHandler;
import company.handlers.xml.XmlEventHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user50 on 24.07.2015.
 */
public class MergeXmlEventHandlerFactory {

    List<Factory<XmlEventHandler>> handlersFactories;

    public MergeXmlEventHandlerFactory(List<Factory<XmlEventHandler>> handlersFactories) {
        this.handlersFactories = handlersFactories;
    }

    public XmlEventHandler get()
    {
        List<XmlEventHandler> handlers = new ArrayList<>();

        for (Factory<XmlEventHandler> handlersFactory : handlersFactories)
            handlers.add(handlersFactory.get());

        return new SuccessiveXmlEventHandler(handlers);
    }
}
