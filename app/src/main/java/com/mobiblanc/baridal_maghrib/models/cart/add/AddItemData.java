
package com.mobiblanc.baridal_maghrib.models.cart.add;

import com.google.gson.annotations.Expose;
import com.mobiblanc.baridal_maghrib.models.common.Header;

public class AddItemData {

    @Expose
    private Header header;
    @Expose
    private AddItemResponseData response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public AddItemResponseData getResponse() {
        return response;
    }

    public void setResponse(AddItemResponseData response) {
        this.response = response;
    }

}
