package com.mobiblanc.gbam.models.payment.recap;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

public class PaymentRecapData {

    @Expose
    private Header header;
    @Expose
    private PaymentRecapResponse response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public PaymentRecapResponse getResponse() {
        return response;
    }

    public void setResponse(PaymentRecapResponse response) {
        this.response = response;
    }

}
