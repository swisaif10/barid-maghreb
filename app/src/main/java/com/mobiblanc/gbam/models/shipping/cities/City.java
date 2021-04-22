package com.mobiblanc.gbam.models.shipping.cities;

import com.google.gson.annotations.Expose;

public class City {
    @Expose
    private String name;
    @Expose
    private String id;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }
}
