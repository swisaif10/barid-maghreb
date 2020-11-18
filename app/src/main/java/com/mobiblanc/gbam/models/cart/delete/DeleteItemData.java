
package com.mobiblanc.gbam.models.cart.delete;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

public class DeleteItemData {

    @Expose
    private Header header;
    @Expose
    private Boolean response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

}
