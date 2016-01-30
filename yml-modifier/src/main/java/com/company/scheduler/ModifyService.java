package com.company.scheduler;

import com.company.ModifierConfig;
import com.company.ModifierXmlEventHandlerProvider;
import com.company.http.HttpClientProvider;
import com.company.http.HttpRequestProvider;
import com.company.http.HttpResponseHandler;
import com.company.http.HttpService;
import com.company.logger.ProcessLogger;
import com.company.readerproviders.DownloadPriceListRequest;
import com.company.readerproviders.SaveIntoFileHttpResponseHandler;
import company.StAXService;
import company.handlers.xml.XmlEventHandler;
import company.providers.FileXMLEventReaderProvider;
import company.providers.XMLEventReaderProvider;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Created by Naya on 20.01.2016.
 */
public class ModifyService {

    private static final ProcessLogger logger = ProcessLogger.INSTANCE;


    public void process(ModifierConfig config)  {

        if (config.getInputFileURL()!=null){

            try {
                //String psw = new String(Base64.getDecoder().decode(config.getPsw().getBytes()));
                HttpService httpService = new HttpService(new HttpClientProvider(config.getUser(), config.getPsw()).get());
                HttpRequestProvider requestProvider = new DownloadPriceListRequest(config.getInputFileURL());
                HttpResponseHandler<String> responseHandler = new SaveIntoFileHttpResponseHandler(config.getEncoding());
                config.setInputFile(httpService.execute(requestProvider, responseHandler));
            } catch (IOException e) {
                //throw new RuntimeException("Unable to download an input file");
                logger.warning("Unable to do auto merge of " + config.getId());
                e.printStackTrace();
            }
        }
        XMLEventReaderProvider readerProvider = new FileXMLEventReaderProvider(config.getInputFile(), config.getEncoding());

        StAXService stAXService = new StAXService( readerProvider );

        try {
            XmlEventHandler handler = new ModifierXmlEventHandlerProvider(config).get();
            stAXService.process(handler);
        } catch (FileNotFoundException | UnsupportedEncodingException | XMLStreamException e) {
            e.printStackTrace();
        }

    }
}
