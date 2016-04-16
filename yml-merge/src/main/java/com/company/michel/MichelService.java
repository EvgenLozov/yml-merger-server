package com.company.michel;

import com.company.config.MergerConfig;
import com.company.readerproviders.HttpXMLEventReaderProvider;
import com.company.service.MergeService;
import company.StAXService;
import company.handlers.xml.XmlEventHandler;
import company.http.HttpClientProvider;
import company.http.HttpService;
import company.providers.FileXMLEventReaderProvider;
import company.providers.XMLEventReaderProvider;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author Yevhen
 */
public class MichelService implements MergeService{


    @Override
    public void process(MergerConfig config) throws IOException, XMLStreamException {

        List<HttpXMLEventReaderProvider> httpProviders = new ArrayList<>();

        if (!config.getUrls().isEmpty()) {
            String psw = new String(Base64.getDecoder().decode(config.getPsw().getBytes()));
            CloseableHttpClient httpClient = new HttpClientProvider(config.getUser(), psw).get();
            HttpService httpService = new HttpService(httpClient);

            for (String url : config.getUrls()) {
                HttpXMLEventReaderProvider provider = new HttpXMLEventReaderProvider(httpService, url, config.getEncoding());

                httpProviders.add(provider);
            }
        }

        if (httpProviders.isEmpty())
            throw new RuntimeException("Must be specified at least one price list source");

        XmlEventHandler michelHandler = new MichelXmlEventHandlerProvider(config, httpProviders).get();

        StAXService service = new StAXService(httpProviders.get(0));

        service.process(michelHandler);

        for (HttpXMLEventReaderProvider httpProvider : httpProviders) {
            httpProvider.removeTmpFile();
        }
    }
}
