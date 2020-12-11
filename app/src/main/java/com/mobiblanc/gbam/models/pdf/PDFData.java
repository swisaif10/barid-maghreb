package com.mobiblanc.gbam.models.pdf;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

public class PDFData {

    @Expose
    private Header header;
    @Expose
    private PDFResponse response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public PDFResponse getResponse() {
        return response;
    }

    public void setResponse(PDFResponse response) {
        this.response = response;
    }
}
