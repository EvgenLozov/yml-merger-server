package com.company.factories.handler;

import company.Currency;
import company.Factory;
import company.handlers.xml.XmlEventHandler;
import company.handlers.xml.currency.ChangeYmlCurrencyHandler;

public class CurrencyHandlerFactory implements Factory<XmlEventHandler> {

    Currency currency;

    public CurrencyHandlerFactory(Currency currency) {
        this.currency = currency;
    }

    @Override
    public XmlEventHandler get() {
        return new ChangeYmlCurrencyHandler(currency);
    }
}
