import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
 
interface BenefitTier {
  id: number;
  minRange: number;
  maxRange: number;
  pricePerUnit: number;
  benefitMasterId: number;
}

interface BenefitItem {
  id: number;
  name: string;
  description: string;
  unitLabel: string;
  tiers: BenefitTier[];
  selectedTierId?: number;
}


@Component({
  selector: 'app-benefit-payment',
  templateUrl: './benefit-payment.component.html',
  styleUrls: ['./benefit-payment.component.scss']
})
export class BenefitPaymentComponent implements OnInit {
 benefits: BenefitItem[] = [];
  discountInput: string = '';

  private baseUrl = 'http://localhost:8080/api/v1/benefits';
  private stripe = 'http://localhost:8080/api/stripe/create-invoice';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get<BenefitItem[]>(this.baseUrl).subscribe(data => {
      this.benefits = data.map(b => ({ ...b, quantity: 0 }));
    });
  }
getSelectedTier(item: BenefitItem): BenefitTier | undefined {
    return item.tiers.find(t => t.id === item.selectedTierId);
  }

 getTierTotal(item: BenefitItem): number {
  const tier = this.getSelectedTier(item);
  return tier ? tier.pricePerUnit : 0;
}


  getSubtotal(): number {
    return this.benefits.reduce((sum, item) => sum + this.getTierTotal(item), 0);
  }

  parseDiscount(): number {
    const raw = this.discountInput.replace('%', '');
    const val = parseFloat(raw);
    return isNaN(val) ? 0 : val / 100;
  }

  getFinalTotal(): number {
    const subtotal = this.getSubtotal();
    const discount = this.parseDiscount();
    return subtotal - subtotal * discount;
  }

  onTierChange(item: BenefitItem) {
    // Optional: show dynamic feedback or validate
  }

submitPayment() {
  try {
    const selectedItems = this.benefits
      .filter(b => b.selectedTierId !== undefined)
      .map(b => {
        const tier = b.tiers.find(t => t.id === b.selectedTierId);

        if (!tier) {
          throw new Error(`Tier not found for benefit: ${b.name}`);
        }

        return {
          benefitMasterId: b.id,
          benefitName: b.name,
          selectedTierId: tier.id,
          price: tier.pricePerUnit,
          total: tier.pricePerUnit
        };
      });

    const discountPercent = this.parseDiscount(); // e.g. 0.25 from "25%"
    const subtotal = selectedItems.reduce((sum, item) => sum + item.total, 0);
    const total = subtotal - subtotal * discountPercent;

    const payload = {
      customerEmail: "thiruna2394@gmail.com",
      items: selectedItems,
      discountPercent,
     };

    console.log('✅ Final Payload:', payload);

this.http.post(this.stripe, payload).subscribe((res: any) => {
  console.log('✅ Payment request created:', res);
  if (!res.invoiceUrl) {
    throw new Error('Invoice URL not returned from server');
  }
  window.open(res.invoiceUrl); // Open Stripe invoice
});

 
  } catch (error) {
    console.error('❌ Error generating payment payload:', error);
    alert('Error: ' + (error as Error).message);
  }
}





  resetForm() {
    this.benefits.forEach(b => b.selectedTierId = undefined);
    this.discountInput = '';
  }
}