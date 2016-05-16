package com.company.allowedcategories;

import com.company.config.MergerConfig;
import company.http.HttpClientProvider;
import company.http.HttpService;
import com.company.logger.ProcessLogger;
import company.http.DownloadPriceListRequest;
import com.company.readerproviders.ExtractCategory;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Yevhen
 */
public class AllCategoriesProvider {
    private static final ProcessLogger logger = ProcessLogger.INSTANCE;

    private MergerConfig config;

    public AllCategoriesProvider(MergerConfig config) {
        this.config = config;
    }

    public Set<Category> get() throws XMLStreamException {
        String psw = new String(Base64.getDecoder().decode(config.getPsw().getBytes()));
        CloseableHttpClient httpClient = new HttpClientProvider(config.getUser(), psw).get();


        HttpService httpService = new HttpService(httpClient);

        Set<Category> categories = new HashSet<>();
        for (String url : config.getUrls())
            try {
                logger.info("Getting categories from file: " + url);
                categories.addAll(httpService.execute(new DownloadPriceListRequest(url), new ExtractCategory(config.getEncoding()), 1000 * 60, 100));
                logger.info("Categories are extracted successfully from: " + url);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        return categories;
    }
}
