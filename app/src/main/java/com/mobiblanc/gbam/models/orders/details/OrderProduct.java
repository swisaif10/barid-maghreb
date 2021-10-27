package com.mobiblanc.gbam.models.orders.details;

public class OrderProduct {
    private final String sku;
    private final String name;
    private final int quantity;
    private final double totalPrice;

    public OrderProduct(String sku, String name, int quantity, double totalPrice) {
        this.sku = sku;
        this.name = name;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
