package com.mobiblanc.gbam.models.dashboard;

import com.google.gson.annotations.Expose;

public class Service {

    @Expose
    private String icon;
    @Expose
    private int id;
    @Expose
    private int serviceOrder;
    @Expose
    private String title;
    @Expose
    private String view;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(int serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

}
