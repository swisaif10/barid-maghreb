package com.mobiblanc.gbam.models.contact.objects;

import com.google.gson.annotations.Expose;

public class Object {
    @Expose
    private String subject;
    private Boolean selected = false;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
