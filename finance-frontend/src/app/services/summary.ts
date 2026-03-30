import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface MonthlySummary {
  startDate: string;
  endDate: string;
  totalIncome: number;
  totalExpense: number;
  netBalance: number;
}

@Injectable({ providedIn: 'root' })
export class SummaryService {
  private api = 'http://localhost:8080/api/summary';

  constructor(private http: HttpClient) {}

  getSummary(startDate: string, endDate: string): Observable<MonthlySummary> {
    return this.http.get<MonthlySummary>(`${this.api}?startDate=${startDate}&endDate=${endDate}`);
  }
}
