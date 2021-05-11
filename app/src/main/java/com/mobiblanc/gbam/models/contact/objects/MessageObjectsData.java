package com.mobiblanc.gbam.models.contact.objects;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

import java.util.ArrayList;
import java.util.List;

public class MessageObjectsData {
    @Expose
    private List<Object> response;
    @Expose
    private Header header;

    public List<Object> getResponse() {
        return response;
    }

    public void setResponse(List<Object> response) {
        this.response = response;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<String> getObjectsNames() {
        List<String> names = new ArrayList<>();
        for (Object object : response) {
            names.add(object.getSubject());
        }
        return names;
    }
}