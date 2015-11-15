package com.company.repository;

import com.company.allowedcategories.AllCategoriesProvider;
import com.company.allowedcategories.Category;
import com.company.config.Config;
import com.company.repository.ConfigRepository;

import javax.xml.stream.XMLStreamException;
import java.util.Set;

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

        Set<Category> categories = new AllCategoriesProvider(config).get();

        return categories;
    }
}
