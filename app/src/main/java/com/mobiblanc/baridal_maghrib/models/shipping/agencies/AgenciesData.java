package com.mobiblanc.baridal_maghrib.models.shipping.agencies;

import com.google.gson.annotations.Expose;
import com.mobiblanc.baridal_maghrib.models.common.Header;

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
