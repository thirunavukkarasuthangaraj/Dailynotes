import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EntryFormComponent } from './components/entry-form/entry-form.component';
import { EntryListComponent } from './components/entry-list/entry-list.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { BenefitTierComponent } from './components/benefit-tier/benefit-tier.component';
import { BenefitPaymentComponent } from './components/benefit-payment/benefit-payment.component';
import { PaymentComponent } from './components/payment/payment.component';

const routes: Routes = [
  // { path: '', component: CheckoutComponent },
  { path: 'add', component: EntryFormComponent },
  { path: 'benefit-tiers', component: BenefitTierComponent },
  { path: 'payment', component: PaymentComponent },
  { path: 'benefit-select', component: BenefitPaymentComponent },
  { path: '', redirectTo: 'payment', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {



}
