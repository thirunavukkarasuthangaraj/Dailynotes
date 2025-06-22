package com.thiru.daliyworklog.daliyworklog.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity	
@Table(name = "benefit_master")
public class BenefitMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String unitLabel;
    
    @OneToMany(mappedBy = "benefitMaster", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<BenefitTier> tiers = new ArrayList<>();


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUnitLabel() {
		return unitLabel;
	}

	public void setUnitLabel(String unitLabel) {
		this.unitLabel = unitLabel;
	}

	public List<BenefitTier> getTiers() {
		return tiers;
	}

	public void setTiers(List<BenefitTier> tiers) {
		this.tiers = tiers;
	}
    
    
}