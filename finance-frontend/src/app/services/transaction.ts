import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';

export interface TransactionRequest {
  description: string;
  amount: number;
  type: 'INCOME' | 'EXPENSE';
  date: string;
  categoryId: number | null;
  notes: string | null;
}

export interface TransactionResponse {
  id: number;
  description: string;
  amount: number;
  type: 'INCOME' | 'EXPENSE';
  date: string;
  categoryName: string | null;
  notes: string | null;
  status: string;
}

export interface TransactionListModel {
  transactions: TransactionResponse[];
  count: number;
}

export interface TransactionFilter {
  type?: string;
  categoryId?: number;
  startDate?: string;
  endDate?: string;
  page?: number;
  size?: number;
}

export interface PagedTransactionResponse {
  transactions: TransactionResponse[];
  currentPage: number;
  totalPages: number;
  totalElements: number;
  size: number;
}

@Injectable({ providedIn: 'root' })
export class TransactionService {
  private api = 'http://localhost:8080/api/transactions';

  constructor(private http: HttpClient) {}

  getAll(filter: TransactionFilter = {}): Observable<PagedTransactionResponse> {
    let params = new HttpParams();
    Object.entries(filter).forEach(([k, v]) => {
      if (v !== undefined && v !== null) params = params.set(k, v);
    });
    return this.http.get<PagedTransactionResponse>(`${this.api}`, { params }).pipe(
      map(data => ({
        ...data,
        transactions: data.transactions.map(t => ({
          ...t,
          type: t.type.toUpperCase() as 'INCOME' | 'EXPENSE'
        }))
      }))
    );
  }

  create(request: TransactionRequest): Observable<TransactionResponse> {
    return this.http.post<TransactionResponse>(this.api, request);
  }

  update(id: number, request: TransactionRequest): Observable<TransactionResponse> {
    return this.http.put<TransactionResponse>(`${this.api}/${id}`, request);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }
}
