package com.mobiblanc.gbam.models.tracking;

import com.google.gson.annotations.Expose;

public class TrackingItem {
    @Expose
    private String etat;
    @Expose
    private String dateEtat;
    @Expose
    private String lieu;

    public String getStatus() {
        return etat;
    }

    public void setStatus(String status) {
        this.etat = status;
    }

    public String getDate() {
        return dateEtat;
    }

    public void setDate(String date) {
        this.dateEtat = date;
    }

    public String getLocation() {
        return lieu;
    }

    public void setLocation(String lieu) {
        this.lieu = lieu;
    }
}
