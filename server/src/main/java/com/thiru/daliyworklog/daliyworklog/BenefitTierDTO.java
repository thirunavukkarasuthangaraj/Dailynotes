package com.thiru.daliyworklog.daliyworklog;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BenefitTierDTO {
	private Long id;
	private Long benefitMasterId;
	private Integer minRange;
	private Integer maxRange;
	private BigDecimal pricePerUnit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBenefitMasterId() {
		return benefitMasterId;
	}

	public void setBenefitMasterId(Long benefitMasterId) {
		this.benefitMasterId = benefitMasterId;
	}

	public Integer getMinRange() {
		return minRange;
	}

	public void setMinRange(Integer minRange) {
		this.minRange = minRange;
	}

	public Integer getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(Integer maxRange) {
		this.maxRange = maxRange;
	}

	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

}