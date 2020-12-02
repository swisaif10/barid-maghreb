package com.mobiblanc.gbam.models.shipping.agencies;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Agency implements Serializable {

    @Expose
    private String address;
    @Expose
    private String faxe;
    @Expose
    private int id;
    @Expose
    private String latitude;
    @Expose
    private String logo;
    @Expose
    private String longitude;
    @Expose
    private String telephone;
    @Expose
    private String title;
    @Expose
    private String province;
    @Expose
    private String time;
    @Expose
    private String region;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFaxe() {
        return faxe;
    }

    public void setFaxe(String faxe) {
        this.faxe = faxe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
