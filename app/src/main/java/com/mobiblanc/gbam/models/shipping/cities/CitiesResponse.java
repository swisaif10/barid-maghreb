package com.mobiblanc.gbam.models.shipping.cities;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class CitiesResponse {
    @Expose
    private List<City> cities;
    @Expose
    private Other other;

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public Other getOther() {
        return other;
    }

    public void setOther(Other other) {
        this.other = other;
    }

    public List<String> getCitiesNames() {
        List<String> names = new ArrayList<>();
        for (City country : cities) {
            names.add(country.getName());
        }
        return names;
    }
}
