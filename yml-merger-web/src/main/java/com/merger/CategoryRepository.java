package com.merger;

import com.company.allowedcategories.Category;
import com.company.config.CategoryIdsPair;
import com.company.config.Config;

import javax.xml.stream.XMLStreamException;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author Yevhen
 */
public class CategoryRepository {

    private static final Logger logger = Logger.getLogger(CategoryRepository.class.getSimpleName());

    private Map<String, Set<Category>> categoriesCache = new HashMap<>();
    
    private CategorySource categorySource;

    public CategoryRepository(CategorySource categorySource) {
        this.categorySource = categorySource;
    }

    public Set<Category> children(String configId, String parentId) throws XMLStreamException {
        Set<Category> allCategories = categoriesCache.get(configId);

        Set<Category> children = new HashSet<>();

        if (allCategories == null)
            return children;

        for (Category category : allCategories) {
            if (category.getParentId() != null && category.getParentId().equals(parentId))
                children.add(category);
        }

        return children;
    }

    public void addOrUpdateCache(Config config) throws XMLStreamException {
        Set<Category> categories = categorySource.get(config.getId());

        for (CategoryIdsPair pair : config.getParentIds()) {
            categories.stream().filter(category -> category.getId().equals(pair.getCategoryId()))
                               .forEach(category -> category.setParentId(pair.getParentId()));
        }
        categoriesCache.put(config.getId(), categories);

        logger.info("Categories cache for config #" + config + " was updated");
    }

    public Category setParent(Config config, String categoryId, String parentId) {
        Set<Category> categories = categoriesCache.get(config.getId());

        for (Category category : categories) {
                if (category.getId().equals(categoryId)){
                    category.setParentId(parentId);
                    return category;
                }
        }

        return null;
    }
}
