
package com.mobiblanc.baridal_maghrib.models.dashboard;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {

    @Expose
    private Boolean active;
    @Expose
    private List<Object> childrenData;
    @Expose
    private int id;
    @Expose
    private String level;
    @Expose
    private String name;
    @Expose
    private String position;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Object> getChildrenData() {
        return childrenData;
    }

    public void setChildrenData(List<Object> childrenData) {
        this.childrenData = childrenData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
