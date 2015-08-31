package com.merger;

import com.company.allowedcategories.Category;

import javax.xml.stream.XMLStreamException;
import java.util.Set;

/**
 * @author Yevhen
 */
public interface CategoryRepository {
    public Set<Category> children(String configId, String parentId) throws XMLStreamException;
}
