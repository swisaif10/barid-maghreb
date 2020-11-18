package com.mobiblanc.gbam.models.shipping.agencies;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

public class AgenciesData {

    @Expose
    private Header header;
    @Expose
    private AgenciesResponse response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public AgenciesResponse getResponse() {
        return response;
    }

    public void setResponse(AgenciesResponse response) {
        this.response = response;
    }

}
