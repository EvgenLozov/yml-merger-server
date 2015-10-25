package com.merger;

import com.company.allowedcategories.AllCategoriesProvider;
import com.company.allowedcategories.Category;
import com.company.config.Config;
import com.company.http.HttpClientProvider;
import com.company.http.HttpService;
import com.company.readerproviders.FileXMLEventReaderProvider;
import com.company.readerproviders.HttpXMLEventReaderProvider;
import company.XMLEventReaderProvider;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.xml.stream.XMLStreamException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yevhenlozov
 */
public class CategorySource {

    private ConfigRepository configRepository;

    public CategorySource(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public Set<Category> get(String configId) throws XMLStreamException {
        Config config = configRepository.get(configId);

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

        readerProviders.addAll(config.getFiles().stream()
                .map(fileName -> new FileXMLEventReaderProvider(fileName, config.getEncoding()))
                .collect(Collectors.toList()));

        Set<Category> categories = new AllCategoriesProvider(config).get();

        httpProviders.forEach(com.company.readerproviders.HttpXMLEventReaderProvider::removeTmpFile);

        return categories;
    }
}
