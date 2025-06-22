package com.thiru.daliyworklog.daliyworklog.model;
import jakarta.persistence.*;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "benefit_tier")
 public class BenefitTier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer minRange;
    private Integer maxRange;
    private BigDecimal pricePerUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "benefit_master_id", nullable = false)
    @JsonBackReference
    private BenefitMaster benefitMaster;
    @Transient
    private Long benefitMasterId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public BenefitMaster getBenefitMaster() {
		return benefitMaster;
	}

	public void setBenefitMaster(BenefitMaster benefitMaster) {
		this.benefitMaster = benefitMaster;
	}
	
	public Long getBenefitMasterId() {
	   
		return benefitMaster != null ? benefitMaster.getId() : benefitMasterId;
	}

	public void setBenefitMasterId(Long benefitMasterId) {
	    this.benefitMasterId = benefitMasterId;
	}

    
 }
