package com.mobiblanc.gbam.models.shipping.address;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class AddressResponse implements Serializable {
    @Expose
    private List<Address> addresses;

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
