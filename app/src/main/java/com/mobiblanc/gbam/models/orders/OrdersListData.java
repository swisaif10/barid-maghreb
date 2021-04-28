package com.mobiblanc.gbam.models.orders;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

import java.util.List;

public class OrdersListData {
    @Expose
    private List<Order> response;
    @Expose
    private Header header;

    public List<Order> getResponse() {
        return response;
    }

    public void setResponse(List<Order> response) {
        this.response = response;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }
}