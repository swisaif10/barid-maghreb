package com.mobiblanc.gbam.models.tracking;

import com.google.gson.annotations.Expose;

public class TrackingItem {
    @Expose
    private String status;
    @Expose
    private String date;

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
}
