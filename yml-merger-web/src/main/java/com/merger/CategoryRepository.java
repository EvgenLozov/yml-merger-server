package com.merger;

import com.company.allowedcategories.Category;

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

    public void addOrUpdateCache(String configId) throws XMLStreamException {
        categoriesCache.put(configId, categorySource.get(configId));
        logger.info("Categories cache for config #" + configId + " was updated");
    }
}
