package com.mobiblanc.gbam.models.payment.recap.info;

import com.google.gson.annotations.Expose;

public class RecapInfoResponse {
    @Expose
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
