
package com.mobiblanc.baridal_maghrib.models.cart.guest;

import com.google.gson.annotations.Expose;
import com.mobiblanc.baridal_maghrib.models.common.Header;

public class GuestCartData {

    @Expose
    private Header header;
    @Expose
    private GuestCartResponse response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public GuestCartResponse getResponse() {
        return response;
    }

    public void setResponse(GuestCartResponse response) {
        this.response = response;
    }

}
