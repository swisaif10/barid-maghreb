package com.mobiblanc.baridal_maghrib.models.authentication.registration;

import com.google.gson.annotations.Expose;
import com.mobiblanc.baridal_maghrib.models.common.Header;

public class RegistrationData {

    @Expose
    private Header header;
    @Expose
    private RegistrationResponseData response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public RegistrationResponseData getResponse() {
        return response;
    }

    public void setResponse(RegistrationResponseData response) {
        this.response = response;
    }

}
