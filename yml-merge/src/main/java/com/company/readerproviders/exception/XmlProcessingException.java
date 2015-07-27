package com.company.readerproviders.exception;

import com.company.http.HttpResponseHandleException;

/**
 * Created by user50 on 04.07.2015.
 */
public class XmlProcessingException extends HttpResponseHandleException {
    public XmlProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
