package com.thiru.daliyworklog.daliyworklog.model;

public class BenefitItemDTO {
	 private Long benefitMasterId;
	    private String benefitName;
	    private Long selectedTierId;
	    private double price;
	    private double total;
		public Long getBenefitMasterId() {
			return benefitMasterId;
		}
		public void setBenefitMasterId(Long benefitMasterId) {
			this.benefitMasterId = benefitMasterId;
		}
		public String getBenefitName() {
			return benefitName;
		}
		public void setBenefitName(String benefitName) {
			this.benefitName = benefitName;
		}
		public Long getSelectedTierId() {
			return selectedTierId;
		}
		public void setSelectedTierId(Long selectedTierId) {
			this.selectedTierId = selectedTierId;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public double getTotal() {
			return total;
		}
		public void setTotal(double total) {
			this.total = total;
		}
	    
}
