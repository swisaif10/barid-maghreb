package com.mobiblanc.gbam.models.products;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

import java.util.List;

public class ProductsData {

    @Expose
    private Header header;
    @Expose
    private List<Product> response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<Product> getResponse() {
        return response;
    }

    public void setResponse(List<Product> response) {
        this.response = response;
    }

}
