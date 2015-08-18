package com.company.config;

/**
 * Created by yevhenlozov on 18.08.15.
 */
public class CategoryIdsPair {
    private String categoryId;
    private String parentId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
