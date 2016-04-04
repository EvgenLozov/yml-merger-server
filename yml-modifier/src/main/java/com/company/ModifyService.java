package com.company;

import com.company.providers.ModifierXmlEventHandlerProvider;
import com.company.providers.SplitXmlEventHandlerProvider;
import company.StAXService;
import company.handlers.xml.XmlEventHandler;
import company.http.*;
import company.providers.FileXMLEventReaderProvider;
import company.providers.XMLEventReaderProvider;
import company.stream.ChainInputStreamOperator;
import company.stream.InputStreamOperator;
import company.stream.XmlInputStreamOperator;
import company.stream.storage.InMemoryStorage;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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

        try {
            List<InputStreamOperator> operatorChain = new ArrayList<>();
            operatorChain.add(new XmlInputStreamOperator(config.getEncoding(), new ModifierXmlEventHandlerProvider(config).get(), new InMemoryStorage()));
            operatorChain.add(new XmlInputStreamOperator(config.getEncoding(), new SplitXmlEventHandlerProvider(config).get(), new InMemoryStorage()));

            InputStreamOperator resultOperator = new ChainInputStreamOperator(operatorChain);

            resultOperator.apply(new FileInputStream(config.getInputFile()));
        } catch (FileNotFoundException | UnsupportedEncodingException | XMLStreamException e) {
            e.printStackTrace();
        }

    }
}
