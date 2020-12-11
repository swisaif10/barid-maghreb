package com.mobiblanc.gbam.models.dashboard;

import java.util.List;
import com.google.gson.annotations.Expose;

public class Category {

    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private String description;
    @Expose
    private String viewCategory;
    @Expose
    private String image;
    @Expose
    private List<Object> childrenData;
    @Expose
    private Boolean withPopUp;
    @Expose
    private String targetPopUp;
    @Expose
    private String descriptionGlobal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getViewCategory() {
        return viewCategory;
    }

    public void setViewCategory(String viewCategory) {
        this.viewCategory = viewCategory;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Object> getChildrenData() {
        return childrenData;
    }

    public void setChildrenData(List<Object> childrenData) {
        this.childrenData = childrenData;
    }

    public Boolean getWithPopUp() {
        return withPopUp;
    }

    public void setWithPopUp(Boolean withPopUp) {
        this.withPopUp = withPopUp;
    }

    public String getTargetPopUp() {
        return targetPopUp;
    }

    public void setTargetPopUp(String targetPopUp) {
        this.targetPopUp = targetPopUp;
    }

    public String getDescriptionGlobal() {
        return descriptionGlobal;
    }

    public void setDescriptionGlobal(String descriptionGlobal) {
        this.descriptionGlobal = descriptionGlobal;
    }
}
