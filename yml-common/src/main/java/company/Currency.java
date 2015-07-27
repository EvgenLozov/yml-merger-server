package company;

/**
 * Created by user50 on 26.07.2015.
 */
public enum Currency {

    RUR("priceRu"), BYR("priceBy"), KZT("priceKz"), UAH("priceUa");

    String elementName;

    Currency(String elementName) {
        this.elementName = elementName;
    }

    public String getElementName() {
        return elementName;
    }
}
