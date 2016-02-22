package com.company.readerproviders.exception;

import company.http.HttpResponseHandleException;

/**
 * Created by user50 on 04.07.2015.
 */
public class IOResponseHandleException extends HttpResponseHandleException {
    public IOResponseHandleException(String message, Throwable cause) {
        super(message, cause);
    }
}
