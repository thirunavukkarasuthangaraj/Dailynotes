import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EntryFormComponent } from './components/entry-form/entry-form.component';
import { EntryListComponent } from './components/entry-list/entry-list.component';
import { HttpClientModule } from '@angular/common/http';
import { NgxEditorModule } from 'ngx-editor';
import { ModalModule } from 'ngx-bootstrap/modal';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { BenefitTierComponent } from './components/benefit-tier/benefit-tier.component';
import { BenefitPaymentComponent } from './components/benefit-payment/benefit-payment.component';
import { PaymentComponent } from './components/payment/payment.component';
//

@NgModule({
  declarations: [
    AppComponent,
    EntryFormComponent,
    EntryListComponent,
    CheckoutComponent,
    BenefitTierComponent,
    BenefitPaymentComponent,
    PaymentComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule, 
    BrowserModule,
    FormsModule,
     HttpClientModule,
    NgxEditorModule,
    ModalModule.forRoot(),


  ],
  providers: [],
  exports: [EntryFormComponent, EntryListComponent, CheckoutComponent, BenefitTierComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  bootstrap: [AppComponent]
})
export class AppModule { }
