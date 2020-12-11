package com.mobiblanc.gbam.models.payment.operation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentOperationResponse {

    @SerializedName("is_absolute")
    private Boolean isAbsolute;
    @Expose
    private String url;

    public Boolean getAbsolute() {
        return isAbsolute;
    }

    public void setAbsolute(Boolean absolute) {
        isAbsolute = absolute;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
