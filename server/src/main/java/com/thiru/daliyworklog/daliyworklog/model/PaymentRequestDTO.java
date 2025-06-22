package com.thiru.daliyworklog.daliyworklog.model;

import java.util.List;

public class PaymentRequestDTO { 
     private String customerEmail;
    private List<BenefitItemDTO> items;
    private double discountPercent;
    private double subtotal;
    private double total;
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public List<BenefitItemDTO> getItems() {
		return items;
	}
	public void setItems(List<BenefitItemDTO> items) {
		this.items = items;
	}
	public double getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(double discountPercent) {
		this.discountPercent = discountPercent;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
    
  
}
