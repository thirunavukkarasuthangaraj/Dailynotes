package com.thiru.daliyworklog.daliyworklog.model;

import lombok.Data;
import java.util.List;

@Data
public class InvoiceRequest {
    private String customerEmail;
    private Long planId;
    private List<AddonItem> items;
    private long discountPercentage;
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public Long getPlanId() {
		return planId;
	}
	public void setPlanId(Long planId) {
		this.planId = planId;
	}
	public List<AddonItem> getItems() {
		return items;
	}
	public void setItems(List<AddonItem> items) {
		this.items = items;
	}
	public long getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(long discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
 
    
    
}