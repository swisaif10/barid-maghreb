package com.mobiblanc.gbam.models.dashboard;

import com.google.gson.annotations.Expose;
import com.mobiblanc.gbam.models.common.Header;

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

    public DashboardResponseData getDashboardResponseData() {
        return response;
    }

    public void setDashboardResponseData(DashboardResponseData dashboardResponseData) {
        this.response = dashboardResponseData;
    }

    @Override
    public String toString() {
        return "DashboardData{" +
                ", response=" + response +
                '}';
    }
}
