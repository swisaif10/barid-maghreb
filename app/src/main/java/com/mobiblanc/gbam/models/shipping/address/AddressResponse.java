package com.mobiblanc.gbam.models.shipping.address;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;

public class AddressResponse implements Serializable {
    @Expose
    private ArrayList<Address> addresses;

    public ArrayList<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<Address> addresses) {
        this.addresses = addresses;
    }
}
