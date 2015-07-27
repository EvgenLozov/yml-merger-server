package com.company.readerproviders;

import com.company.http.HttpRequestProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Created by user50 on 21.06.2015.
 */
public class DownloadPriceListRequest implements HttpRequestProvider {

    String url;

    public DownloadPriceListRequest(String url) {
        this.url = url;
    }

    @Override
    public HttpRequestBase getRequest() {
        return new HttpGet(url);
    }

    @Override
    public String getHost() {
        return "www.apishops.com";
    }
}
