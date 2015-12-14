package com.company.operators;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class OfferDescriptionProvider {
    private String template;
    private String encoding;

    public OfferDescriptionProvider(String template, String encoding) {
        this.template = template;
        this.encoding = encoding;
    }

    public String get(String url){
        try {
            if (Charset.forName(encoding).toString().equals("UTF-8"))
                return template.replace(":url", url);
            else
                return new String(template.replace(":url", url).getBytes(), encoding);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
