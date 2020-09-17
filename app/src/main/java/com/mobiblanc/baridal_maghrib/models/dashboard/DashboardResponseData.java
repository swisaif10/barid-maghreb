
package com.mobiblanc.baridal_maghrib.models.dashboard;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class DashboardResponseData implements Serializable {

    @Expose
    private List<Category> categories;
    @Expose
    private List<Menu> menu;
    @Expose
    private List<Service> services;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

}
