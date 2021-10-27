package com.mobiblanc.gbam.models.contact.objects;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

import java.util.ArrayList;
import java.util.List;

public class MessageObjectsData {
    @Expose
    private Response response;
    @Expose
    private Header header;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

}