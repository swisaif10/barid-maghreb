package com.mobiblanc.gbam.models.contact.message;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

public class SendMessageData {
    @Expose
    private Header header;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }
}
