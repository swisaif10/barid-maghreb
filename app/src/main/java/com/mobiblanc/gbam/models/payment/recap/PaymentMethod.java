package com.mobiblanc.gbam.models.payment.recap;

import com.google.gson.annotations.Expose;

public class PaymentMethod {

    @Expose
    private String code;
    @Expose
    private String title;

    public String getTag() {
        return code;
    }

    public void setTag(String code) {
        this.code = code;
    }

    public String getText() {
        return title;
    }

    public void setText(String text) {
        this.title = text;
    }

}
