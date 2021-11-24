package com.mobiblanc.gbam.models.orders.details;

public class TrackingNumber{
    public String tracking;
    public String status;

    public TrackingNumber(String tracking, String status) {
        this.tracking = tracking;
        this.status = status;
    }

    public String getTracking() {
        return tracking;
    }

    public String getStatus() {
        return status;
    }
}

