package com.company.config;

public class FilterParameter {

    boolean image;
    boolean description;
    boolean available;

    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    public boolean isDescription() {
        return description;
    }

    public void setDescription(boolean description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
