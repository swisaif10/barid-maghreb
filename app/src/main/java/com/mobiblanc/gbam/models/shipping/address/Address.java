package com.mobiblanc.gbam.models.shipping.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Address implements Serializable {

    @Expose
    private String adresse;
    @Expose
    private int id;
    @Expose
    private String telephone;
    @SerializedName("address_name")
    private String name;
    @Expose
    private String city;
    @Expose
    private String postcode;
    @Expose
    private String type;
    @Expose
    private String ice;
    @Expose
    private String cni;
    @SerializedName("street_number")
    private String streetNumber;
    @SerializedName("complement_address")
    private String complementAddress;
    @SerializedName("tax_identification")
    private String taxIdentification;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIce() {
        return ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public String getCni() {
        return cni;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getComplementAddress() {
        return complementAddress;
    }

    public void setComplementAddress(String complementAddress) {
        this.complementAddress = complementAddress;
    }

    public String getTaxIdentification() {
        return taxIdentification;
    }

    public void setTaxIdentification(String taxIdentification) {
        this.taxIdentification = taxIdentification;
    }
}
