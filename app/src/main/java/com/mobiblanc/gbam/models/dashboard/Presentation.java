package com.mobiblanc.gbam.models.dashboard;

import com.google.gson.annotations.Expose;

public class Presentation {

    @Expose
    private String description;
    @Expose
    private String labelButton;
    @Expose
    private String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabelButton() {
        return labelButton;
    }

    public void setLabelButton(String labelButton) {
        this.labelButton = labelButton;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Presentation{" +
                "description='" + description + '\'' +
                ", labelButton='" + labelButton + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
