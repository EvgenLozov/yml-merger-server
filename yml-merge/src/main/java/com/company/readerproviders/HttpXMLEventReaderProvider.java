package com.company.readerproviders;

import company.http.DownloadPriceListRequest;
import company.http.HttpService;
import com.company.logger.ProcessLogger;
import company.http.SaveIntoFileHttpResponseHandler;
import company.providers.XMLEventReaderProvider;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by user50 on 04.07.2015.
 */
public class HttpXMLEventReaderProvider implements XMLEventReaderProvider {
    private static final ProcessLogger logger = ProcessLogger.INSTANCE;

    HttpService httpService;
    String url;
    String encoding;

    String fileName;

    public HttpXMLEventReaderProvider(HttpService httpService, String url, String encoding) {
        this.httpService = httpService;
        this.url = url;
        this.encoding = encoding;
    }

    @Override
    public XMLEventReader get() {
        try {
            if (fileName == null){

                logger.info("Downloading " + url);
                fileName = httpService.execute(new DownloadPriceListRequest(url), new SaveIntoFileHttpResponseHandler(encoding), 1000 * 60, 100);
                logger.info("Downloading " + url + " is completed");
            }

            XMLInputFactory ifactory = XMLInputFactory.newFactory();
            ifactory.setProperty(XMLInputFactory.IS_VALIDATING, false);

            return ifactory.createXMLEventReader(new FileInputStream(fileName), encoding);
        } catch (IOException | XMLStreamException e) {
            throw new RuntimeException("Unable to process url :  " + url + "\n" + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "URL: "+url;
    }

    public void removeTmpFile()
    {
        if (!new File(fileName).delete())
            Logger.getLogger(HttpXMLEventReaderProvider.class.getName()).warning("Unable to delete file: "+fileName);
    }
}
