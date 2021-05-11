package com.mobiblanc.gbam.models.webview;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

public class WebViewData {

    @Expose
    private Header header;
    @Expose
    private WebViewResponse response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public WebViewResponse getResponse() {
        return response;
    }

    public void setResponse(WebViewResponse response) {
        this.response = response;
    }
}
