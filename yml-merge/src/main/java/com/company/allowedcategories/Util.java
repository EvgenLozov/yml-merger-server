package com.company.allowedcategories;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by user50 on 11.07.2015.
 */
public class Util {
    ListMultimap<String, Category> map;

    public Util(ListMultimap<String, Category> map) {
        this.map = map;
    }

    public Set<Category> getDescendants(Category category){
        if (!map.containsKey(category.getId()) || map.get(category.getId()).isEmpty())
            return Sets.newHashSet(category);

        Set<Category> categories = new HashSet<>();

        for (Category child : map.get(category.getId())) {
            categories.addAll(getDescendants(child));
        }

        categories.add(category);

        return categories;
    }

    public Set<Category> getDescendants(Set<Category> categories){
        Set<Category> results = new HashSet<>();

        for (Category category : categories) {
            results.addAll(getDescendants(category));
        }

        return results;
    }
}
