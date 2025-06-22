package com.thiru.daliyworklog.daliyworklog.model;

public class ItemRequest {
    private String item;
    private int quantity;
    private long unitPrice;
    private String priceId;

    // Getters and Setters
    public String getItem() {
        return item;
    }
   

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getPriceId() {
        return priceId;
    }
    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }
}
