
package com.mobiblanc.baridal_maghrib.models.cart.add;

import com.google.gson.annotations.Expose;

public class AddItemResponseData {

    @Expose
    private int itemId;
    @Expose
    private String name;
    @Expose
    private int price;
    @Expose
    private String qty;
    @Expose
    private String quoteId;
    @Expose
    private String sku;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

}
