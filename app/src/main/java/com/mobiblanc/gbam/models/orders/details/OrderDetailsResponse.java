package com.mobiblanc.gbam.models.orders.details;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsResponse {
    @SerializedName("detail_order")
    private List<OrderDetail> orderDetails;

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}