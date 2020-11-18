package com.mobiblanc.gbam.models.shipping.address;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

public class AddressData {

    @Expose
    private Header header;
    @Expose
    private List<Address> response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<Address> getResponse() {
        return response;
    }

    public void setResponse(List<Address> response) {
        this.response = response;
    }

}
