package com.mobiblanc.gbam.models.payment.recap.info;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

public class RecapInfoData {
    @Expose
    private RecapInfoResponse response;
    @Expose
    private Header header;

    public RecapInfoResponse getResponse() {
        return response;
    }

    public void setResponse(RecapInfoResponse response) {
        this.response = response;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }
}
