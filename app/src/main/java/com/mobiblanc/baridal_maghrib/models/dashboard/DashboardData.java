
package com.mobiblanc.baridal_maghrib.models.dashboard;

import com.google.gson.annotations.Expose;
import com.mobiblanc.baridal_maghrib.models.common.Header;

public class DashboardData {

    @Expose
    private Header header;
    @Expose
    private DashboardResponseData response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public DashboardResponseData getResponse() {
        return response;
    }

    public void setResponse(DashboardResponseData response) {
        this.response = response;
    }

}
