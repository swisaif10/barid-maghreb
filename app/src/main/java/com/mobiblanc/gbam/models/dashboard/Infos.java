package com.mobiblanc.gbam.models.dashboard;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Infos {

    @Expose
    private List<Category> categories;
    @Expose
    private Presentation presentation;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Presentation getPresentation() {
        return presentation;
    }

    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }

}
