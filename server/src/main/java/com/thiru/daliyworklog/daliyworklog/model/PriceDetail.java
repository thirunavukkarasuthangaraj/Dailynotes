package com.thiru.daliyworklog.daliyworklog.model;

import lombok.Data;

@Data
public class PriceDetail {
    private String userType;
    private int quantity;
    private long unitPrice;

      // Getters and Setters
    public String getUserType() {
      return userType;
    }
    public void setUserType(String userType) {
      this.userType = userType;
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
    
}

