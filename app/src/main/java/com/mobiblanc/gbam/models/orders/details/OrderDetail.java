package com.mobiblanc.gbam.models.orders.details;

import com.google.gson.annotations.Expose;

public class OrderDetail {
    @Expose
    private String createdAt;
    @Expose
    private int id;
    @Expose
    private String trackingNumber;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

}
