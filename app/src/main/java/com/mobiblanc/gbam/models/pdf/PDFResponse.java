package com.mobiblanc.gbam.models.pdf;

import com.google.gson.annotations.Expose;

public class PDFResponse {
    @Expose
    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
