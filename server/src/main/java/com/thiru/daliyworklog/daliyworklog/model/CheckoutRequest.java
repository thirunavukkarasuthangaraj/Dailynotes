package com.thiru.daliyworklog.daliyworklog.model;

import java.util.List;

public class CheckoutRequest {
    private String name;
    private String currency;
    private String email;
    private List<ItemRequest> priceDetails;

    // Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public List<ItemRequest> getPriceDetails() {
        return priceDetails;
    }
    public void setPriceDetails(List<ItemRequest> priceDetails) {
        this.priceDetails = priceDetails;
    }
}
