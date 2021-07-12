package com.mobiblanc.gbam.models.shipping.cities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobiblanc.gbam.models.common.Header;

import java.util.ArrayList;

public class WayListData {
    @Expose
    private Header header;

    @Expose
    @SerializedName("response")
    private ArrayList<String> ways;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public ArrayList<String> getWays() {
        return ways;
    }

    public void setWays(ArrayList<String> ways) {
        this.ways = ways;
    }
}
