package com.mobiblanc.gbam.models;

public class HistoryItem {
    private int color;
    private String status;

    public HistoryItem(int color, String status) {
        this.color = color;
        this.status = status;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
