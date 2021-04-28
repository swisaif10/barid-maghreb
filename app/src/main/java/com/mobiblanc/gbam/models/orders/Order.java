package com.mobiblanc.gbam.models.orders;

import com.google.gson.annotations.Expose;

public class Order {
    @Expose
    private String date;
    @Expose
    private String totalAmount;
    @Expose
    private String orderId;
    @Expose
    private String totalItem;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(String totalItem) {
        this.totalItem = totalItem;
    }
}
