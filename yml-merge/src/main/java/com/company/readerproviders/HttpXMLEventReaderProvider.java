package com.company.readerproviders;

import com.company.http.HttpService;
import company.XMLEventReaderProvider;

import javax.xml.stream.XMLEventReader;
import java.io.IOException;

/**
 * Created by user50 on 04.07.2015.
 */
public class HttpXMLEventReaderProvider implements XMLEventReaderProvider {
    HttpService httpService;
    String url;
    String encoding;

    public HttpXMLEventReaderProvider(HttpService httpService, String url, String encoding) {
        this.httpService = httpService;
        this.url = url;
        this.encoding = encoding;
    }

    @Override
    public XMLEventReader get() {
        try {
            return httpService.execute(new DownloadPriceListRequest(url), new StaxResponseHandler(encoding));
        } catch (IOException e) {
            throw new RuntimeException("Unable to process url :  " + url + "\n" + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "URL: "+url;
    }
}
