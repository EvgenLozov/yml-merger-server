package com.merger;

import com.company.allowedcategories.AllCategoriesProvider;
import com.company.allowedcategories.Category;
import com.company.config.Config;
import com.company.http.HttpClientProvider;
import com.company.http.HttpService;
import com.company.readerproviders.FileXMLEventReaderProvider;
import com.company.readerproviders.HttpXMLEventReaderProvider;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import company.XMLEventReaderProvider;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.xml.stream.XMLStreamException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Yevhen
 */
public class CategoryRepository {

    public static final String ROOT_CATEGORY_ID = "0";

    private Map<String, Set<Category>> categoriesCache = new HashMap<>();
    
    private ConfigRepository configRepository;

    public CategoryRepository(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    public Set<Category> children(String configId, String parentId) throws XMLStreamException {
        Set<Category> allCategories = getAll(configId);

        ListMultimap<String, Category> multimap = ArrayListMultimap.create();

        for (Category category : allCategories) {
            multimap.get(category.getParentId()).add(category);
        }

        if (parentId.equals(ROOT_CATEGORY_ID))
            return new HashSet<>(multimap.get(ROOT_CATEGORY_ID));

        return new HashSet<>(multimap.get(parentId));
    }

    private Set<Category> getAll(String configId) throws XMLStreamException {
        if (categoriesCache.containsKey(configId))
            return categoriesCache.get(configId);
        
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

        Set<Category> categories = new AllCategoriesProvider(readerProviders).get();

        httpProviders.forEach(com.company.readerproviders.HttpXMLEventReaderProvider::removeTmpFile);

        return categories;
    }

    public void addOrUpdateCache(String configId) throws XMLStreamException {
        if (categoriesCache.containsKey(configId))
            categoriesCache.remove(configId);

        categoriesCache.put(configId, getAll(configId));
    }
}
