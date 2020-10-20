package com.mobiblanc.baridal_maghrib.models.payment;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobiblanc.baridal_maghrib.models.common.Item;

public class PaymentRecapResponse {

    @SerializedName("Address")
    private String address;
    @SerializedName("commande_date")
    private String commandeDate;
    @Expose
    private int fees;
    @Expose
    private List<Item> items;
    @SerializedName("payment_methods")
    private List<PaymentMethod> paymentMethods;
    @Expose
    private int productsPrice;
    @SerializedName("shipping_methode")
    private String shippingMethode;
    @Expose
    private int totalPrice;

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

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
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

    public int getProductsPrice() {
        return productsPrice;
    }

    public void setProductsPrice(int productsPrice) {
        this.productsPrice = productsPrice;
    }

    public String getShippingMethode() {
        return shippingMethode;
    }

    public void setShippingMethode(String shippingMethode) {
        this.shippingMethode = shippingMethode;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

}
