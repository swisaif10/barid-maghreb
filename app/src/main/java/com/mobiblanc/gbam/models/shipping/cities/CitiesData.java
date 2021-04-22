package com.mobiblanc.gbam.models.shipping.cities;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

public class CitiesData {
    @Expose
    private Header header;
    @Expose
    private CitiesResponse response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public CitiesResponse getResponse() {
        return response;
    }

    public void setResponse(CitiesResponse response) {
        this.response = response;
    }
}
