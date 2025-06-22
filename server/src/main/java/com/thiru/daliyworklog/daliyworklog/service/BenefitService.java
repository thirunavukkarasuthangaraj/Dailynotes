package com.thiru.daliyworklog.daliyworklog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiru.daliyworklog.daliyworklog.model.BenefitMaster;
import com.thiru.daliyworklog.daliyworklog.model.BenefitTier;
import com.thiru.daliyworklog.daliyworklog.repository.BenefitMasterRepository;
import com.thiru.daliyworklog.daliyworklog.repository.BenefitTierRepository;

 
@Service
public class BenefitService {

    @Autowired
    private BenefitMasterRepository benefitMasterRepo;

    @Autowired
    private BenefitTierRepository benefitTierRepo;

    public List<BenefitMaster> getAllBenefits() {
        return benefitMasterRepo.findAll();
    }

    public List<BenefitTier> getTiersByBenefitId(Long benefitId) {
        return benefitTierRepo.findByBenefitMaster_Id(benefitId);
    }

    public BenefitTier addTier(BenefitTier tier) {
        if (tier.getBenefitMasterId() != null) {
            BenefitMaster master = benefitMasterRepo.findById(tier.getBenefitMasterId())
                .orElseThrow(() -> new RuntimeException("BenefitMaster not found with id: " + tier.getBenefitMasterId()));
            tier.setBenefitMaster(master);
        } else {
            throw new RuntimeException("benefitMasterId is required to create BenefitTier.");
        }

        return benefitTierRepo.save(tier);  // ✅ Now it saves with FK
    }




    public BenefitTier updateTier(Long id, BenefitTier updatedTier) {
        BenefitTier existing = benefitTierRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Tier not found"));
        existing.setMinRange(updatedTier.getMinRange());
        existing.setMaxRange(updatedTier.getMaxRange());
        existing.setPricePerUnit(updatedTier.getPricePerUnit());
        return benefitTierRepo.save(existing);
    }

    public void deleteTier(Long id) {
        benefitTierRepo.deleteById(id);
    }
}
