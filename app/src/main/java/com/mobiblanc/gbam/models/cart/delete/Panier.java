package com.mobiblanc.gbam.models.cart.delete;

import com.google.gson.annotations.Expose;

public class Panier {
    @Expose
    private Float productsPrice;
    @Expose
    private Float fees;
    @Expose
    private Float totalPrice;

    public Float getProductsPrice() {
        return productsPrice;
    }

    public void setProductsPrice(Float productsPrice) {
        this.productsPrice = productsPrice;
    }

    public Float getFees() {
        return fees;
    }

    public void setFees(Float fees) {
        this.fees = fees;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
