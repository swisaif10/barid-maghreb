
package com.mobiblanc.gbam.models.cart.items;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Item;

import java.util.List;

public class CartItemsResponseData {

    @Expose
    private float fees;
    @Expose
    private List<Item> items;
    @Expose
    private float productsPrice;
    @Expose
    private float totalPrice;

    public float getFees() {
        return fees;
    }

    public void setFees(float fees) {
        this.fees = fees;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public float getProductsPrice() {
        return productsPrice;
    }

    public void setProductsPrice(float productsPrice) {
        this.productsPrice = productsPrice;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

}
