package com.mobiblanc.baridal_maghrib.models.authentication.registration;

import com.google.gson.annotations.Expose;

public class RegistrationResponseData {

    @Expose
    private String email;
    @Expose
    private String firstname;
    @Expose
    private String lastname;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}
