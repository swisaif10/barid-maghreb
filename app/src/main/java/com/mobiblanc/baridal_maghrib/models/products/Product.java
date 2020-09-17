
package com.mobiblanc.baridal_maghrib.models.products;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {

    @Expose
    private String description;
    @Expose
    private int id;
    @Expose
    private List<String> image;
    @Expose
    private String name;
    @Expose
    private String price;
    @Expose
    private String shortDescription;
    @Expose
    private String sku;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

}
