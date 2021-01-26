package com.mobiblanc.gbam.models.payment.recap;

import com.google.gson.annotations.Expose;

public class PaymentMethod {

    @Expose
    private String code;
    @Expose
    private String title;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
