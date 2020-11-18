package com.mobiblanc.gbam.models.tracking;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

import java.util.List;

public class TrackingData {

    @Expose
    private Header header;
    @Expose
    private List<TrackingItem> response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<TrackingItem> getResponse() {
        return response;
    }

    public void setResponse(List<TrackingItem> response) {
        this.response = response;
    }
}
