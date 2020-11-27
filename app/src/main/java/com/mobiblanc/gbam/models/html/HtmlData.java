package com.mobiblanc.gbam.models.html;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

public class HtmlData {

    @Expose
    private Header header;
    @Expose
    private HtmlResponse response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public HtmlResponse getResponse() {
        return response;
    }

    public void setResponse(HtmlResponse response) {
        this.response = response;
    }
}
