package com.company.repository;

import com.company.allowedcategories.AllCategoriesProvider;
import com.company.allowedcategories.Category;
import com.company.config.MergerConfig;
import company.config.ConfigRepository;

import javax.xml.stream.XMLStreamException;
import java.util.Set;

/**
 * @author yevhenlozov
 */
public class CategorySource {

    private ConfigRepository<MergerConfig> configRepository;

    public CategorySource(ConfigRepository<MergerConfig> configRepository) {
        this.configRepository = configRepository;
    }

    public Set<Category> get(String configId) throws XMLStreamException {
        MergerConfig config = configRepository.get(configId);

        Set<Category> categories = new AllCategoriesProvider(config).get();

        return categories;
    }
}
