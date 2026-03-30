import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

export interface CategoryRequest {
  name: string;
  type: 'INCOME' | 'EXPENSE';
}

export interface CategoryResponse {
  id: number;
  name: string;
  type: 'INCOME' | 'EXPENSE';
}

export interface CategoryListModel {
  categories: CategoryResponse[];
  count: number;
}

@Injectable({ providedIn: 'root' })
export class CategoryService {
  private api = 'http://localhost:8080/api/categories';

  constructor(private http: HttpClient) {}

  getAll(): Observable<CategoryListModel> {
    return this.http.get<CategoryListModel>(this.api).pipe(
      map(data => ({
        ...data,
        categories: data.categories.map(c => ({
          ...c,
          type: c.type.toUpperCase() as 'INCOME' | 'EXPENSE'
        }))
      }))
    );
  }

  create(request: CategoryRequest): Observable<CategoryResponse> {
    return this.http.post<CategoryResponse>(this.api, request);
  }
}
