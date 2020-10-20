
package com.mobiblanc.baridal_maghrib.models.shipping.address;

import com.google.gson.annotations.Expose;

public class Address {

    @Expose
    private String adresse;
    @Expose
    private int id;
    @Expose
    private String telephone;
    private Boolean selected;

    public Address(String address, int id, String telephone, Boolean selected) {
        this.adresse = address;
        this.id = id;
        this.telephone = telephone;
        this.selected = selected;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
