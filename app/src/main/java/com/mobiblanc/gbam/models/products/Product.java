package com.mobiblanc.gbam.models.products;

import com.google.gson.annotations.Expose;
import java.io.Serializable;

public class Product implements Serializable {

    @Expose
    private int id;
    @Expose
    private String image;
    @Expose
    private String name;
    @Expose
    private String price;
    @Expose
    private String labelPrice;
    @Expose
    private String sku;
    private int quantity = 1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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

    public String getLabelPrice() {
        return labelPrice;
    }

    public void setLabelPrice(String labelPrice) {
        this.labelPrice = labelPrice;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
