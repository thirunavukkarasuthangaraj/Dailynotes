package com.thiru.daliyworklog.daliyworklog.model;

import java.util.List;

public class SubscriptionRequestDTO {
	   public String customerEmail;
	    public List<AddonItem> items;
	    public Long  discountPercentage; // optional
		public String getCustomerEmail() {
			return customerEmail;
		}
		public void setCustomerEmail(String customerEmail) {
			this.customerEmail = customerEmail;
		}
		public List<AddonItem> getItems() {
			return items;
		}
		public void setItems(List<AddonItem> items) {
			this.items = items;
		}
		public Long getDiscountPercentage() {
			return discountPercentage;
		}
		public void setDiscountPercentage(Long discountPercentage) {
			this.discountPercentage = discountPercentage;
		}
	 
	    
	    
}
