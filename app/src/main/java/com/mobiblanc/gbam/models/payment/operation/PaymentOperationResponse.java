package com.mobiblanc.gbam.models.payment.operation;

import com.google.gson.annotations.Expose;

public class PaymentOperationResponse {

    @Expose
    private String redirect;
    @Expose
    private String url;

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
