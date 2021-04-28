package com.mobiblanc.gbam.models.orders.details;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

public class OrderDetailsData {
    @Expose
    private OrderDetailsResponse response;
    @Expose
    private Header header;

    public OrderDetailsResponse getResponse() {
        return response;
    }

    public void setResponse(OrderDetailsResponse response) {
        this.response = response;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }
}
