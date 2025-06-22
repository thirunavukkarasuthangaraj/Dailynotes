package com.thiru.daliyworklog.daliyworklog.model;

import lombok.Data;

@Data
public class AddonItem {
	private String benefitName;
    private Integer quantity;
    private Long amount;
    private String description;
    
 
    
    
		public String getBenefitName() {
			return benefitName;
		}
		public void setBenefitName(String benefitName) {
			this.benefitName = benefitName;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public Long getAmount() {
			return amount;
		}
		public void setAmount(Long amount) {
			this.amount = amount;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
	    
    
}