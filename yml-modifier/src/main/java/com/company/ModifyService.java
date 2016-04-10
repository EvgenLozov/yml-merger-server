package com.company;

import com.company.ModifierConfig;
import com.company.ModifierXmlEventHandlerProvider;
import company.StAXService;
import company.config.Config;
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


        XMLEventReaderProvider readerProvider = config.getInputFileURL() !=null && !config.getInputFileURL().isEmpty() ?
                    getHttpReaderProvider(config):
                    new FileXMLEventReaderProvider(config.getInputFile(), config.getEncoding());

        StAXService stAXService = new StAXService( readerProvider );

        try {
            if (config.getLimitSize() >= 0) {
                XmlEventHandler handler = new WriteToLimitSizeFile(config.getOutputDir(), config.getEncoding(), config.getLimitSize());
                stAXService.process(handler);
            }
            else {
                XmlEventHandler handler = new ModifierXmlEventHandlerProvider(config).get();
                stAXService.process(handler);
            }

        } catch (FileNotFoundException | UnsupportedEncodingException | XMLStreamException e) {
            e.printStackTrace();
        }

    }

    private XMLEventReaderProvider getHttpReaderProvider(ModifierConfig config)
    {
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

            String tmpFile = httpService.execute(requestProvider, responseHandler);

            return new FileXMLEventReaderProvider(tmpFile, config.getEncoding());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to do auto merge of " + config.getId());
        }
    }
}
