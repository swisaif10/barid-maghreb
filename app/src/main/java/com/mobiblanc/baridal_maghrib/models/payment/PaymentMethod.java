package com.mobiblanc.baridal_maghrib.models.payment;

import com.google.gson.annotations.Expose;

public class PaymentMethod {

    @Expose
    private String tag;
    @Expose
    private String text;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
