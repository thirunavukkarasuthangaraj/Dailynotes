import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CheckoutModel } from '../models/product.model';
 
@Injectable({ providedIn: 'root' })
export class ProductService {
  constructor(private http: HttpClient) {}
  createSession(data: CheckoutModel) {
    return this.http.post<any>('http://localhost:8080/api/payment/create-checkout-session', data);
  }
}
