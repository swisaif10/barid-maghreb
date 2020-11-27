package com.mobiblanc.gbam.models.shipping.address;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

import java.io.Serializable;

public class AddressData implements Serializable {

    @Expose
    private Header header;
    @Expose
    private AddressResponse response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public AddressResponse getResponse() {
        return response;
    }

    public void setResponse(AddressResponse response) {
        this.response = response;
    }

}
