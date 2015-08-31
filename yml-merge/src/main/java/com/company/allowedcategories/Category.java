package com.company.allowedcategories;

/**
 * Created by user50 on 11.07.2015.
 */
public class Category {
    private String id;
    private String parentId;
    private String name;
    private boolean checked;

    public Category(String id, String parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    public Category(String id, String parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    public Category(String id, String parentId, String name, boolean checked) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != null ? !id.equals(category.id) : category.id != null) return false;
        if (parentId != null ? !parentId.equals(category.parentId) : category.parentId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
