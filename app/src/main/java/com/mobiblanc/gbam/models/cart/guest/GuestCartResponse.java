package com.mobiblanc.gbam.models.cart.guest;

import com.google.gson.annotations.Expose;

public class GuestCartResponse {

    @Expose
    private String quoteId;

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

}
