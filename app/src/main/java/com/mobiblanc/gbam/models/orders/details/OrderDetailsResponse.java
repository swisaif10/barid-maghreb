package com.mobiblanc.gbam.models.orders.details;

import com.google.gson.annotations.SerializedName;
import com.mobiblanc.gbam.models.products.Product;

import java.util.List;

public class OrderDetailsResponse {
    @SerializedName("detail_order")
    private List<OrderDetail> orderDetails;
    private List<OrderProduct> products;
    @SerializedName("show_message")
    public boolean showMessage;
    @SerializedName("message_empty_parcels")
    public String messageEmptyParcels;

    public List<OrderProduct> getProducts() {
        return products;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public boolean isShowMessage() {
        return showMessage;
    }

    public String getMessageEmptyParcels() {
        return messageEmptyParcels;
    }
}