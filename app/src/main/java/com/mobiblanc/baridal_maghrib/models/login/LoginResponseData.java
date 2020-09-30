
package com.mobiblanc.baridal_maghrib.models.login;

import com.google.gson.annotations.Expose;

public class LoginResponseData {

    @Expose
    private Boolean assignToCart;
    @Expose
    private String quoteId;

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

}
