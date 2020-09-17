
package com.mobiblanc.baridal_maghrib.models.products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobiblanc.baridal_maghrib.models.common.Header;

import java.util.List;

public class ProductsData {

    @Expose
    private Header header;
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
