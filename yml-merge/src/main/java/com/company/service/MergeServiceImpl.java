package com.company.service;

import com.company.config.CategoryIdsPair;
import com.company.config.MergerConfig;
import com.company.config.Replace;
import com.company.http.HttpClientProvider;
import com.company.http.HttpService;
import com.company.processing.MergePostProcessor;
import com.company.processing.MergedYmlSource;
import com.company.processing.ReplaceProcessing;
import company.config.ConfigRepository;
import company.providers.FileXMLEventReaderProvider;
import com.company.readerproviders.HttpXMLEventReaderProvider;
import company.providers.XMLEventReaderProvider;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.*;

public class MergeServiceImpl implements MergeService {

    private ConfigRepository<MergerConfig> configRepository;

    public MergeServiceImpl(ConfigRepository<MergerConfig> configRepository) {
        this.configRepository = configRepository;
    }

    public void process(MergerConfig config) throws IOException, XMLStreamException {
        List<XMLEventReaderProvider> readerProviders = new ArrayList<>();

        List<HttpXMLEventReaderProvider> httpProviders = new ArrayList<>();

        if (!config.getUrls().isEmpty()) {
            String psw = new String(Base64.getDecoder().decode(config.getPsw().getBytes()));
            CloseableHttpClient httpClient = new HttpClientProvider(config.getUser(), psw).get();
            HttpService httpService = new HttpService(httpClient);

            for (String url : config.getUrls()) {
                HttpXMLEventReaderProvider provider = new HttpXMLEventReaderProvider(httpService, url, config.getEncoding());

                readerProviders.add(provider);
                httpProviders.add(provider);
            }
        }

        for (String fileName : config.getFiles())
            readerProviders.add(new FileXMLEventReaderProvider(fileName, config.getEncoding()));

        byte[] bytes = new MergedYmlSource(config, readerProviders).provide();

        ReplaceProcessing processing = new ReplaceProcessing(config.getEncoding(), getReplaces(config));
        bytes = processing.process(bytes);

        new MergePostProcessor(config.getEncoding(), config.getCurrencies(), config.getOutputFile(), config.getOldPrice()).process(bytes);

        for (HttpXMLEventReaderProvider httpProvider : httpProviders) {
            httpProvider.removeTmpFile();
        }

        config.setLastMerge(System.currentTimeMillis());
        configRepository.save(config);
    }

    private List<Replace> getReplaces(MergerConfig config)
    {
        List<Replace> replaces = new ArrayList<>();

        replaces.addAll(config.getReplaces());

        for (CategoryIdsPair pair : config.getParentIds()) {
            Set<String> words = new HashSet<>();
            words.add("<category id=\""+pair.getCategoryId()+"\" parentId=\".*\">");
            words.add("<category id=\""+pair.getCategoryId()+"\">");

            replaces.add(new Replace(words,"<category id=\""+pair.getCategoryId()+"\" parentId=\""+pair.getParentId()+"\">"));
        }

        return replaces;
    }


}
