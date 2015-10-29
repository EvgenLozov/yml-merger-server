package com.company.allowedcategories;

import com.company.config.Config;
import com.company.http.HttpClientProvider;
import com.company.http.HttpService;
import com.company.readerproviders.DownloadPriceListRequest;
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

    private Config config;

    public AllCategoriesProvider(Config config) {
        this.config = config;
    }

    public Set<Category> get() throws XMLStreamException {
        String psw = new String(Base64.getDecoder().decode(config.getPsw().getBytes()));
        CloseableHttpClient httpClient = new HttpClientProvider(config.getUser(), psw).get();


        HttpService httpService = new HttpService(httpClient);

        Set<Category> categories = new HashSet<>();
        for (String url : config.getUrls())
            try {
                categories.addAll(httpService.execute(new DownloadPriceListRequest(url), new ExtractCategory(config.getEncoding())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        return categories;
    }
}
