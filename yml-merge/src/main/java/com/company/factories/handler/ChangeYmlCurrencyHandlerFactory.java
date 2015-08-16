package com.company.factories.handler;

import company.Currency;
import company.Factory;
import company.handlers.xml.XmlEventHandler;
import company.handlers.xml.currency.ChangeYmlCurrencyHandler;

/*
* ���������� �������� ������������ ����� ��������� ���� ������ ������
* */
public class ChangeYmlCurrencyHandlerFactory implements Factory<XmlEventHandler> {

    Currency currency;

    public ChangeYmlCurrencyHandlerFactory(Currency currency) {
        this.currency = currency;
    }

    @Override
    public XmlEventHandler get() {
        return new ChangeYmlCurrencyHandler(currency);
    }
}
