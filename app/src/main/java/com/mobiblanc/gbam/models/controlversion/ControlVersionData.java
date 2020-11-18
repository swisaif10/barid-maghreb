
package com.mobiblanc.gbam.models.controlversion;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

public class ControlVersionData {

    @Expose
    private Header header;
    @Expose
    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
