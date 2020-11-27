package com.mobiblanc.gbam.models.account.checkotp;

import com.google.gson.annotations.Expose;

public class CheckOTPResponseData {

    @Expose
    private Boolean assignToCart;
    @Expose
    private String quoteId;
    @Expose
    private String name;

    private String token;

    public Boolean getAssignToCart() {
        return assignToCart;
    }

    public void setAssignToCart(Boolean assignToCart) {
        this.assignToCart = assignToCart;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
