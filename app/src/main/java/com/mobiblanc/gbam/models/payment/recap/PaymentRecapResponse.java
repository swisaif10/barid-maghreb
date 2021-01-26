package com.mobiblanc.gbam.models.payment.recap;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobiblanc.gbam.models.common.Item;

public class PaymentRecapResponse {

    @SerializedName("Address")
    private String address;
    @SerializedName("commande_date")
    private String commandeDate;
    @Expose
    private float fees;
    @Expose
    private List<Item> items;
    @SerializedName("payment_methods")
    private List<PaymentMethod> paymentMethods;
    @Expose
    private float productsPrice;
    @SerializedName("shipping_methode")
    private String shippingMethode;
    @Expose
    private float totalPrice;
    @SerializedName("address_id")
    private String addressId;
    @SerializedName("agence_id")
    private String agenceId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCommandeDate() {
        return commandeDate;
    }

    public void setCommandeDate(String commandeDate) {
        this.commandeDate = commandeDate;
    }

    public float getFees() {
        return fees;
    }

    public void setFees(float fees) {
        this.fees = fees;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public float getProductsPrice() {
        return productsPrice;
    }

    public void setProductsPrice(float productsPrice) {
        this.productsPrice = productsPrice;
    }

    public String getShippingMethode() {
        return shippingMethode;
    }

    public void setShippingMethode(String shippingMethode) {
        this.shippingMethode = shippingMethode;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAgenceId() {
        return agenceId;
    }

    public void setAgenceId(String agenceId) {
        this.agenceId = agenceId;
    }
}
