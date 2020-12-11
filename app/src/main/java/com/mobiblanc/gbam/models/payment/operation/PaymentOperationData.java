package com.mobiblanc.gbam.models.payment.operation;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

public class PaymentOperationData {
    @Expose
    private Header header;
    @Expose
    private PaymentOperationResponse response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public PaymentOperationResponse getResponse() {
        return response;
    }

    public void setResponse(PaymentOperationResponse response) {
        this.response = response;
    }
}
