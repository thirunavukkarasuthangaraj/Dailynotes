import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { loadStripe } from '@stripe/stripe-js';
 
// TypeScript model
interface ItemRequest {
  priceId: string;
  quantity: number;
}

@Component({
  selector: 'app-root',
  templateUrl: './checkout.component.html'
})
export class CheckoutComponent {
  private stripePromise = loadStripe('pk_test_51NuDgVFv9MYngj7rMlG8PqP3O6wQx0VXYijJneUXp0m6ZQRmuqjlnEUiSK9jzBbUn5O9RAbnEhOTVVKCfaQ6oKGE001EGjg4p8'); // Replace with your Stripe public key

  constructor(private http: HttpClient) { }

  async payNow() {
    // Example request payload  
    const payload = {
      name: 'ScaleX',
      currency: 'usd',
      email: 'thiruna2394@gmail.com',

      // month price id
      priceDetails: [
        { item: 'User', quantity: 2, unitPrice: 100, priceId: 'price_1RZ9tjFv9MYngj7rTQza4IYX' },
        { item: 'Referral', quantity: 3, unitPrice: 100, priceId: 'price_1RZ9wDFv9MYngj7ryEjJk66W' },
        { item: 'Connection', quantity: 1, unitPrice: 200, priceId: 'price_1RZ9uvFv9MYngj7rrJ7xbwJM' }
      ]
    }; 

    try {
      const response: any = await this.http
        .post('http://localhost:8080/api/payment/create-checkout-session', payload)
        .toPromise();

      if (response?.url) {
        window.location.href = response.url;

      } else {
        console.error('URL not found in response');
      }
    } catch (error) {
      console.error('Error creating session:', error);
    }
  }
}