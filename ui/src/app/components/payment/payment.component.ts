import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface AddonOption {
  label: string;
  quantity: number;
  price: number; // in dollars/rupees
}

interface SkuItem {
  label: string;
  included: number;
  unit: string;
  options: AddonOption[];
  selectedAddon?: AddonOption;
}

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.scss']
})
export class PaymentComponent {
  customerEmail = 'thiru.t@Mavens-i.com'; 
  discountPercent: number = 0;

  // Base plan in rupees/dollars
  basePlanPrice: number = 999;

  skuItems: SkuItem[] = [
    {
      label: 'Job/Candidate Slots',
      included: 50,
      unit: 'slots',
      options: [
        { label: '+10 slots', quantity: 10, price: 200 },
        { label: '+25 slots', quantity: 25, price: 450 },
        { label: '+50 slots', quantity: 50, price: 850 },
        { label: '+100 slots', quantity: 100, price: 1500 }
      ]
    },
    {
      label: 'Active conversations',
      included: 2000,
      unit: 'Conversations',
      options: [
        { label: '+1000 Conversations', quantity: 1000, price: 100 },
        { label: '+2500 Conversations', quantity: 2500, price: 230 },
        { label: '+5000 Conversations', quantity: 5000, price: 425 }
      ]
    },
    {
      label: 'Member connections',
      included: 500,
      unit: 'Connections',
      options: [
        { label: '+1000 Connections', quantity: 1000, price: 40 },
        { label: '+2000 Connections', quantity: 2000, price: 75 },
        { label: '+5000 Connections', quantity: 5000, price: 180 }
      ]
    },
    {
      label: 'B2B Connections',
      included: 500,
      unit: 'Connections',
      options: [
        { label: '+100 Connections', quantity: 100, price: 50 },
        { label: '+250 Connections', quantity: 250, price: 120 },
        { label: '+500 Connections', quantity: 500, price: 225 }
      ]
    }
  ];

  constructor(private http: HttpClient) {}

  onAddonChange(item: SkuItem, event: Event): void {
    const selectedLabel = (event.target as HTMLSelectElement).value;
    item.selectedAddon = item.options.find(opt => opt.label === selectedLabel);
  }

  getAddOnTotal(): number {
    return this.skuItems
      .filter(item => item.selectedAddon)
      .reduce((sum, item) => sum + (item.selectedAddon?.price || 0), 0);
  }

  getDiscountValue(): number {
    const total = this.basePlanPrice + this.getAddOnTotal();
    return Math.round(total * (this.discountPercent || 0) / 100);
  }

  getFinalTotal(): number {
    const totalBeforeDiscount = this.basePlanPrice + this.getAddOnTotal();
    const discount = this.getDiscountValue();
    return Math.max(0, totalBeforeDiscount - discount);
  }

  onDiscountChange(event: any) {
    this.discountPercent = Number(event.target.value) || 0;
  }

  reset(): void {
    this.skuItems.forEach(item => (item.selectedAddon = undefined));
    this.discountPercent = 0;
  }
  calculateDiscountPercentage(): number {
  const totalBeforeDiscount = this.basePlanPrice + this.getAddOnTotal();
  if (totalBeforeDiscount === 0) return 0;
  
  const discountAmount = this.getDiscountValue();
  return Math.round((discountAmount / totalBeforeDiscount) * 100);
}


  requestPayment(): void {
    const payload = {
      customerEmail: this.customerEmail,
      items: [
        {
          benefitName: 'Base Plan',
          quantity: 1,
          amount: this.basePlanPrice * 100, // in paise/cents
          description: 'ScaleX Base Plan ₹999'
        },
        ...this.skuItems
          .filter(item => item.selectedAddon)
          .map(item => ({
            benefitName: item.label,
            quantity: item.selectedAddon?.quantity,
            amount: (item.selectedAddon?.price || 0) * 100, // in paise/cents
            description: item.selectedAddon?.label
          }))
      ],
      discountPercentage: this.calculateDiscountPercentage()
    };

    this.http.post('http://localhost:8080/api/v1/stripe/create-subscription', payload)
      .subscribe({
        next: (res: any) => {
          console.log('Subscription created successfully:', res);
          window.open(res.invoiceUrl, '_blank');
        },
        error: (err) => {
          console.error('Error creating invoice:', err);
          alert('Error creating subscription');
        }
      });
  }
}
