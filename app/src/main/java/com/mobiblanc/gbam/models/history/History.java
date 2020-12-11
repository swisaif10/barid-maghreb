package com.mobiblanc.gbam.models.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {
    @Expose
    private String id;
    @Expose
    private String status;
    @Expose
    private String date;
    @SerializedName("numbre_product")
    private String productsNumber;
    @Expose
    private String totalPrice;
    @Expose
    private String color;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductsNumber() {
        return productsNumber;
    }

    public void setProductsNumber(String productNumber) {
        this.productsNumber = productNumber;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
