package com.company;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.events.Attribute;
import java.util.Map;
import java.util.function.Consumer;

public class OfferAttributeModificator implements Consumer<Map<QName, Attribute>> {
    XMLEventFactory xmlEventFactory;

    public OfferAttributeModificator(XMLEventFactory xmlEventFactory) {
        this.xmlEventFactory = xmlEventFactory;
    }

    @Override
    public void accept(Map<QName, Attribute> attributes) {
        attributes.remove(new QName("","deleted",""));

        QName available = new QName("","available","");

        if (!attributes.containsKey(available) )
            attributes.put(available,xmlEventFactory.createAttribute(available, ""));

        if (attributes.containsKey(available) && attributes.get(available).getValue().equals("false") )
            attributes.put(available,xmlEventFactory.createAttribute(available, ""));
    }
}
