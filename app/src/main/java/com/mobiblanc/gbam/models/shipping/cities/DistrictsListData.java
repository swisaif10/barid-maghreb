package com.mobiblanc.gbam.models.shipping.cities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobiblanc.gbam.models.common.Header;

import java.util.ArrayList;

public class DistrictsListData {

    @Expose
    private Header header;

    @Expose
    @SerializedName("response")
    private ArrayList<String> districts;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public ArrayList<String> getDistricts() {
        return districts;
    }

    public void setDistricts(ArrayList<String> districts) {
        this.districts = districts;
    }
}
