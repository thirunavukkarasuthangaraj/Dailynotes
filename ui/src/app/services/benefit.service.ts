import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
export interface Benefit {
    id: number;
    name: string;
    description: string;
    unitLabel: string;
    tiers: BenefitTier[];
}

export interface BenefitTier {
    id?: number;
    benefitMasterId: number;
    minRange: number;
    maxRange: number;
    pricePerUnit: number;
}

@Injectable({ providedIn: 'root' })
export class BenefitService {
  private baseUrl = 'http://localhost:8080/api/v1/benefits';

  constructor(private http: HttpClient) {}

  getBenefits(): Observable<Benefit[]> {
    return this.http.get<Benefit[]>(`${this.baseUrl}`);
  }

  addTier(benefitId: number, tier: Partial<BenefitTier>): Observable<BenefitTier> {
    return this.http.post<BenefitTier>(`${this.baseUrl}/tiers`, {
      ...tier,
      benefitMasterId: benefitId
    });
  }

  updateTier(tierId: number, tier: Partial<BenefitTier>): Observable<BenefitTier> {
    return this.http.put<BenefitTier>(`${this.baseUrl}/tiers/${tierId}`, tier);
  }

  deleteTier(tierId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/tiers/${tierId}`);
  }
}
