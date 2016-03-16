package com.modifier.web;

import com.company.ModifierConfig;
import com.company.ModifierConfigRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import company.StAXService;
import company.conditions.InElementCondition;
import company.config.JsonBasedConfigRepository;
import company.handlers.xml.WriteEventToFile;
import company.handlers.xml.XmlEventFilter;
import company.handlers.xml.insert.BytesWriter;
import company.http.*;
import company.providers.FileXMLEventReaderProvider;
import company.providers.XMLEventReaderProvider;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.function.Predicate;

/**
 * Created by Naya on 16.03.2016.
 */
public class MainLimitSizeTest {

    public static void main(String[] args){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        ModifierConfigRepository cr = new ModifierConfigRepository(new JsonBasedConfigRepository<>("D:\\Java\\yml-merger-server\\yml-merger-server\\yml-modifier-web\\config\\config.json", ModifierConfig.class,mapper));
        cr.list();
        process(cr.list().get(0));
    }

    public static void process(ModifierConfig config)  {

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

        String commonPartFile ="D:\\Java\\yml-merger-server\\yml-merger-server\\yml-modifier-web\\"+config.getOutputDir() + "/commonPart.xml";
        Predicate<XMLEvent> closeCondition = (event) -> event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("yml_catalog");



        try {
            stAXService.process(new XmlEventFilter(new WriteEventToFile(commonPartFile, config.getEncoding(), closeCondition), new InElementCondition("offers").negate()));

            stAXService.process(new XmlEventFilter(new BytesWriter("D:\\Java\\yml-merger-server\\yml-merger-server\\yml-modifier-web\\"+config.getOutputDir() + "/output.xml", commonPartFile, config.getEncoding(), 40000), new InElementCondition("offers")));
        } catch ( XMLStreamException e) {
            e.printStackTrace();
        }

    }
}
        

