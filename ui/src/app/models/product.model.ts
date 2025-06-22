export interface PriceDetail {
  userType: string;
  quantity: number;
  unitPrice: number;
}

export interface CheckoutModel {
  name: string;
  currency: string;
  email: string;
  priceDetails: PriceDetail[];
}
