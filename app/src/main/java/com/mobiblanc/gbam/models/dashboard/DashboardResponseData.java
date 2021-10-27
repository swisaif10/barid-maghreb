package com.mobiblanc.gbam.models.dashboard;

import com.google.gson.annotations.Expose;

import java.util.List;

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

    @Override
    public String toString() {
        return "DashboardResponseData{" +
                "infos=" + infos +
                ", services=" + services +
                '}';
    }
}
