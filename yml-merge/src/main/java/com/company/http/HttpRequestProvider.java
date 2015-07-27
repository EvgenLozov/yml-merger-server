package com.company.http;

import org.apache.http.client.methods.HttpRequestBase;

/**
 * Created by user50 on 26.05.2015.
 */
public interface HttpRequestProvider {

    HttpRequestBase getRequest();

    String getHost();

}
