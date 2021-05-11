package com.mobiblanc.gbam.models.faqstamp;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

import java.util.List;

public class FAQStampData {

    @Expose
    private Header header;
    @Expose
    private List<FAQItem> response;

    public List<FAQItem> getResponse() {
        return response;
    }

    public void setResponse(List<FAQItem> response) {
        this.response = response;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }
}