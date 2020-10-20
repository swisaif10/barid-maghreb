package com.mobiblanc.baridal_maghrib.models.shipping.agencies;

import java.util.List;
import com.google.gson.annotations.Expose;

public class AgenciesResponse {

    @Expose
    private List<Agency> agencies;

    public List<Agency> getAgencies() {
        return agencies;
    }

    public void setAgencies(List<Agency> agencies) {
        this.agencies = agencies;
    }

}
