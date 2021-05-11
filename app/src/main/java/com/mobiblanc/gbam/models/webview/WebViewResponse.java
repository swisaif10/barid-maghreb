package com.mobiblanc.gbam.models.webview;

import com.google.gson.annotations.Expose;

public class WebViewResponse {
    @Expose
    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
