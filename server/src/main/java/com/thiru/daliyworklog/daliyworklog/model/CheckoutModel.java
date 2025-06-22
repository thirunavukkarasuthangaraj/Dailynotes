package com.thiru.daliyworklog.daliyworklog.model;

import java.util.List;

public class CheckoutModel {
    private String name;
    private String currency;
    private String email;
    private List<PriceDetail> priceDetails;
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
    public List<PriceDetail> getPriceDetails() {
      return priceDetails;
    }
    public void setPriceDetails(List<PriceDetail> priceDetails) {
      this.priceDetails = priceDetails;
    }

    // Getters and Setters

}
