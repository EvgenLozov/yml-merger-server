package com.merger.yml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created with IntelliJ IDEA.
 * User: user50
 * Date: 10.11.13
 * Time: 11:10
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.FIELD)

public class Offer {
    @XmlAttribute
    public String id;

    @XmlAttribute
    public String type;

    @XmlAttribute
    public Boolean available;

    public String url;
    public Double price;
    public Double priceBy;
    public Double priceKz;
    public Double priceRu;
    public String currencyId;
    public String name;
    public String categoryId;
    public String picture;
    public Boolean delivery;
    public String local_delivery_cost;
    public String vendor;
    public String vendorCode;
    public String description;
    public String country_of_origin;
    public String adult;
//    public List<Parameter> param;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Offer offer = (Offer) o;

        if (!id.equals(offer.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
