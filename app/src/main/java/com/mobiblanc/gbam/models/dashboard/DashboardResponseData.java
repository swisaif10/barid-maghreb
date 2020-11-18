package com.mobiblanc.gbam.models.dashboard;

import java.util.List;
import com.google.gson.annotations.Expose;

public class DashboardResponseData {

    @Expose
    private Infos infos;
    @Expose
    private List<Service> services;

    public Infos getInfos() {
        return infos;
    }

    public void setInfos(Infos infos) {
        this.infos = infos;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

}
