package com.mobiblanc.gbam.models.account.checkotp;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

public class CheckOTPData {
    @Expose
    private Header header;
    @Expose
    private CheckOTPResponseData response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public CheckOTPResponseData getResponse() {
        return response;
    }

    public void setResponse(CheckOTPResponseData response) {
        this.response = response;
    }
}
