package com.mobiblanc.gbam.models.dashboard;

import java.util.List;
import com.google.gson.annotations.Expose;

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
