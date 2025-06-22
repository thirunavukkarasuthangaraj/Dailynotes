package com.thiru.daliyworklog.daliyworklog.repository;

 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.thiru.daliyworklog.daliyworklog.model.BenefitTier;

 
@Repository
 

public interface BenefitTierRepository extends JpaRepository<BenefitTier, Long> {
    List<BenefitTier> findByBenefitMaster_Id(Long benefitMasterId);  
}
