package com.mobiblanc.gbam.models.cart.delete;

import com.google.gson.annotations.Expose;

public class DeleteItemResponse {
    @Expose
    private Boolean deleted;
    @Expose
    private Panier panier;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }
}
