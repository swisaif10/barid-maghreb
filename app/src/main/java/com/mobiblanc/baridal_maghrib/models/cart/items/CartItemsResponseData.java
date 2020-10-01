
package com.mobiblanc.baridal_maghrib.models.cart.items;

import com.google.gson.annotations.Expose;

import java.util.List;

public class CartItemsResponseData {

    @Expose
    private int fees;
    @Expose
    private List<CartItem> items;
    @Expose
    private int productsPrice;
    @Expose
    private int totalPrice;

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public int getProductsPrice() {
        return productsPrice;
    }

    public void setProductsPrice(int productsPrice) {
        this.productsPrice = productsPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

}
