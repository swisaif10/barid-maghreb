package com.mobiblanc.gbam.models.account.profile;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

public class ProfileData {
    @Expose
    private Header header;
    @Expose
    private ProfileResponseData response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public ProfileResponseData getResponse() {
        return response;
    }

    public void setResponse(ProfileResponseData response) {
        this.response = response;
    }
}
