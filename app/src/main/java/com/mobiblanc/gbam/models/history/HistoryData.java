package com.mobiblanc.gbam.models.history;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

import java.util.List;

public class HistoryData {
    @Expose
    private Header header;
    @Expose
    private List<History> response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<History> getResponse() {
        return response;
    }

    public void setResponse(List<History> response) {
        this.response = response;
    }
}
