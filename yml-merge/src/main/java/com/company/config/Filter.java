package com.company.config;

import company.Currency;

import java.util.Map;

public class Filter {

    Map<Currency,FilterParameter> parameters;

    public Map<Currency, FilterParameter> getParameters() {
        return parameters;
    }

    public void setParameters(Map<Currency, FilterParameter> parameters) {
        this.parameters = parameters;
    }
}
