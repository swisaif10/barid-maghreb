
package com.mobiblanc.gbam.models.cart.items;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

public class CartItemsData {

    @Expose
    private Header header;
    @Expose
    private CartItemsResponseData response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public CartItemsResponseData getResponse() {
        return response;
    }

    public void setResponse(CartItemsResponseData response) {
        this.response = response;
    }

}
