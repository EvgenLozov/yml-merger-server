package com.merger.controller;

import com.company.allowedcategories.Category;
import com.company.config.CategoryIdsPair;
import com.company.config.Config;
import com.company.repository.CategoryRepository;
import com.company.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Yevhen
 */
@RestController
@RequestMapping("/configs/{id}/categories")
public class CategoriesController {

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping(value = "/{categoryId}", method = RequestMethod.POST)
    public Category setParent(@PathVariable String id,
                              @PathVariable String categoryId,
                              @RequestParam String parentId, HttpServletResponse response) throws XMLStreamException {

        Config config = configRepository.get(id);

        List<CategoryIdsPair> pairs = config.getParentIds();

        boolean isUpdated = false;

        for (CategoryIdsPair pair : pairs) {
            if (pair.getCategoryId().equals(categoryId)){
                pair.setParentId(parentId);
                isUpdated = true;
                break;
            }
        }

        if (!isUpdated){
            pairs.add(new CategoryIdsPair(categoryId, parentId));
            response.setStatus(201);
        }

        configRepository.save(config);

        Category category = categoryRepository.setParent(config, categoryId, parentId);

        return category;
    }

    @RequestMapping(value = "/{categoryId}/children", method = RequestMethod.GET)
    public Set<Category> children(@PathVariable String id, @PathVariable String categoryId) throws XMLStreamException {
        Config config = configRepository.get(id);

        Set<Category> categories = categoryRepository.children(config.getId(), categoryId);

        categories.forEach(category -> category.setChecked(config.getCategoryIds().contains(category.getId())));

        return categories;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void updateCache(@PathVariable String id) {
        Config config = configRepository.get(id);

        new Thread(() -> {
            try {
                categoryRepository.addOrUpdateCache(config);
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @RequestMapping(value = "/cache", method = RequestMethod.GET)
    public long cacheDate(@PathVariable String id) {
        Config config = configRepository.get(id);

        Date date = categoryRepository.getCacheDate(config.getId());
        if (date == null)
            return 0L;
        else
            return date.getTime();
    }

}
