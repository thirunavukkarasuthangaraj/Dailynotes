import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BenefitPaymentComponent } from './benefit-payment.component';

describe('BenefitPaymentComponent', () => {
  let component: BenefitPaymentComponent;
  let fixture: ComponentFixture<BenefitPaymentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BenefitPaymentComponent]
    });
    fixture = TestBed.createComponent(BenefitPaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
