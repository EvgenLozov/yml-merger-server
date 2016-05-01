package com.company;

import com.company.providers.ModifierXmlEventHandlerProvider;
import com.company.providers.OutputXmlEventHandlerProvider;
import company.http.*;
import company.stream.InputStreamOperator;
import company.stream.ReplaceFragmentsOperator;
import company.stream.XmlInputStreamConsumer;
import company.stream.XmlInputStreamOperator;
import company.stream.storage.InMemoryStorage;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.nio.file.Files;
import java.util.function.Supplier;

/**
 * Created by Naya on 20.01.2016.
 */
public class ModifyService {

    public void process(ModifierConfig config)  {

        try {
            InputStreamOperator modify = new XmlInputStreamOperator(config.getEncoding(), new ModifierXmlEventHandlerProvider(config).get(), new InMemoryStorage());
            InputStreamOperator replace = new ReplaceFragmentsOperator(config.getEncoding(), config.getReplaces());

            Supplier<InputStream> inputXmlFileSupplier = getInputStream(config);
            InputStream modifiedXmlFile = modify.andThen(replace).apply(inputXmlFileSupplier.get());

            XmlInputStreamConsumer splitAndStore = new XmlInputStreamConsumer(config.getEncoding(), new OutputXmlEventHandlerProvider(config, inputXmlFileSupplier).get());

            splitAndStore.accept(modifiedXmlFile);
        } catch (FileNotFoundException | UnsupportedEncodingException | XMLStreamException e) {
            throw new RuntimeException(e);
        }

    }

    private Supplier<InputStream> getInputStream(ModifierConfig config) throws FileNotFoundException {
        if (config.getInputFileURL() ==null || config.getInputFileURL().isEmpty()) {
            InputStream inputStream = new FileInputStream(config.getInputFile());
            return () -> inputStream;
        }

        try {
            CloseableHttpClient httpClient = new HttpClientProvider(config.getUser(), config.getPsw()).get();

            HttpService httpService = new HttpService(httpClient);
            HttpRequestProvider requestProvider = new DownloadPriceListRequest(config.getInputFileURL());
            byte[] bytes = httpService.execute(requestProvider, new SaveToByteArrayHttpResponseHandler());

            return () -> new ByteArrayInputStream(bytes);
        } catch (Exception e){
            throw new RuntimeException("Unable to get http reader provider", e);
        }

    }
}
