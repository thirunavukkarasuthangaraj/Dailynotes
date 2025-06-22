package com.thiru.daliyworklog.daliyworklog.controller;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 import com.thiru.daliyworklog.daliyworklog.model.BenefitMaster;
import com.thiru.daliyworklog.daliyworklog.model.BenefitTier;
import com.thiru.daliyworklog.daliyworklog.repository.BenefitMasterRepository;
import com.thiru.daliyworklog.daliyworklog.repository.BenefitTierRepository;
import java.util.List;

@RestController
@RequestMapping("/api/v1/benefits")
@CrossOrigin
public class BenefitController {
    @Autowired private BenefitMasterRepository masterRepo;
    @Autowired private BenefitTierRepository tierRepo;

    @GetMapping
    public List<BenefitMaster> getAllBenefits() {
        return masterRepo.findAll();
    }
    @PostMapping("/tiers")
    public ResponseEntity<BenefitTier> createTier(@RequestBody BenefitTier tier) {
        if (tier.getBenefitMasterId() == null) {
            return ResponseEntity.badRequest().build();
        }

        BenefitMaster master = masterRepo.findById(tier.getBenefitMasterId())
            .orElseThrow(() -> new RuntimeException("BenefitMaster not found"));

        tier.setBenefitMaster(master); // ✅ Attach the relationship

        BenefitTier saved = tierRepo.save(tier);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/tiers/{id}")
    public BenefitTier updateTier(@PathVariable Long id, @RequestBody BenefitTier tier) {
        BenefitTier existing = tierRepo.findById(id).orElseThrow();
        existing.setMinRange(tier.getMinRange());
        existing.setMaxRange(tier.getMaxRange());
        existing.setPricePerUnit(tier.getPricePerUnit());
        return tierRepo.save(existing);
    }

    @DeleteMapping("/tiers/{id}")
    public void deleteTier(@PathVariable Long id) {
        tierRepo.deleteById(id);
    }

    @GetMapping("/{id}/tiers")
    public List<BenefitTier> getTiers(@PathVariable Long id) {
        return tierRepo.findAll()
                       .stream()
                       .filter(t -> t.getBenefitMaster().getId().equals(id))
                       .toList();
    }
}