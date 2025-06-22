import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BenefitTierComponent } from './benefit-tier.component';

describe('BenefitTierComponent', () => {
  let component: BenefitTierComponent;
  let fixture: ComponentFixture<BenefitTierComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BenefitTierComponent]
    });
    fixture = TestBed.createComponent(BenefitTierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
