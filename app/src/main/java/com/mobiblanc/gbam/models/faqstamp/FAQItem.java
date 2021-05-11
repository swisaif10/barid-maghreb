package com.mobiblanc.gbam.models.faqstamp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FAQItem {

    @SerializedName("reponse")
    private String response;
    @Expose
    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
