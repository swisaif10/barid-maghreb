package com.mobiblanc.gbam.models.html;

import com.google.gson.annotations.Expose;

public class HtmlResponse {

    @Expose
    private String title;
    @Expose
    private String html;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
