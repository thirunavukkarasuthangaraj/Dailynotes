import { Component, OnInit } from '@angular/core';
import { Benefit, BenefitService, BenefitTier } from 'src/app/services/benefit.service';

@Component({
  selector: 'app-benefit-management',
  templateUrl: './benefit-tier.component.html',
  styleUrls: ['./benefit-tier.component.scss']
})
export class BenefitTierComponent implements OnInit {

  showModal = false;
  editMode = false;
  selectedBenefit: Benefit | BenefitTier | null = null;
  newTier: Partial<BenefitTier> = {};


  benefits: Benefit[] = [];
  selectedBenefitId: number | null = null;
  editTierId: number | null = null;




  constructor(private benefitService: BenefitService) { }

  ngOnInit(): void {
    this.loadBenefits();
  }

  loadBenefits(): void {
    this.benefitService.getBenefits().subscribe((data: any) => {
      this.benefits = data;
    });
  }

  openAddModal(benefit: Benefit) {
    this.selectedBenefit = benefit;
    this.selectedBenefitId = benefit.id;   
    this.newTier = {};
    this.editMode = false;
    this.showModal = true;
  }

  openEditModal(benefit: Benefit, tier: BenefitTier) {
    this.selectedBenefit = benefit;
    this.newTier = { ...tier };
    this.editMode = true;
    this.showModal = true;
  }

  saveTier(): void {
    if (!this.selectedBenefitId) return;

    if (this.editMode && this.editTierId !== null) {
      this.benefitService.updateTier(this.editTierId, this.newTier).subscribe(() => {
        this.loadBenefits();
        this.closeModal();
      });
    } else {
      this.benefitService.addTier(this.selectedBenefitId, this.newTier).subscribe(() => {
        this.loadBenefits();
        this.closeModal();
      });
    }
  }

  deleteTier(tierId: number): void {
    if (confirm('Are you sure you want to delete this tier?')) {
      this.benefitService.deleteTier(tierId).subscribe(() => {
        this.loadBenefits();
      });
    }
  }
  confirmDelete(tier: BenefitTier) {
    if (confirm('Are you sure to delete this tier?')) {
      // DELETE call here
    }
  }


  closeModal() {
    this.showModal = false;
    this.newTier = {};
  }
}