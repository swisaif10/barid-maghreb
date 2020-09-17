
package com.mobiblanc.baridal_maghrib.models.dashboard;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Menu implements Serializable {

    @Expose
    private int id;
    @Expose
    private int orderMenu;
    @Expose
    private String title;
    @Expose
    private String type;
    @Expose
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderMenu() {
        return orderMenu;
    }

    public void setOrderMenu(int orderMenu) {
        this.orderMenu = orderMenu;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
