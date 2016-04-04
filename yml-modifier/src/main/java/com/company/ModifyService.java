package com.company;

import com.company.ModifierConfig;
import com.company.ModifierXmlEventHandlerProvider;
import company.StAXService;
import company.handlers.xml.WriteToLimitSizeFile;
import company.handlers.xml.XmlEventHandler;
import company.http.*;
import company.providers.FileXMLEventReaderProvider;
import company.providers.XMLEventReaderProvider;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Naya on 20.01.2016.
 */
public class ModifyService {

    public void process(ModifierConfig config)  {

        if (config.getInputFileURL()!=null && !config.getInputFileURL().isEmpty()){

            try {
                CloseableHttpClient httpClient;
                try {
                    httpClient = new HttpClientProvider(config.getUser(), config.getPsw()).get();
                } catch (Exception e){
                    e.printStackTrace();
                    throw new RuntimeException("Unable to get HttpClient");
                }
                HttpService httpService = new HttpService(httpClient);
                HttpRequestProvider requestProvider = new DownloadPriceListRequest(config.getInputFileURL());
                HttpResponseHandler<String> responseHandler = new SaveIntoFileHttpResponseHandler(config.getEncoding());
                config.setInputFile(httpService.execute(requestProvider, responseHandler));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to do auto merge of " + config.getId());
            }
        }

        XMLEventReaderProvider readerProvider = new FileXMLEventReaderProvider(config.getInputFile(), config.getEncoding());

        StAXService stAXService = new StAXService( readerProvider );

        try {
           // XmlEventHandler handler = new ModifierXmlEventHandlerProvider(config).get();
            XmlEventHandler handler = new WriteToLimitSizeFile(config.getOutputDir(),config.getEncoding(),800000l);
            stAXService.process(handler);
        } catch (/*FileNotFoundException | UnsupportedEncodingException |*/ XMLStreamException e) {
            e.printStackTrace();
        }

    }
}
