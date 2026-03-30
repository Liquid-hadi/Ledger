import { Routes } from '@angular/router';
import { Dashboard } from './pages/dashboard/dashboard';
import { Transactions } from './pages/transactions/transactions';

export const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: Dashboard },
  { path: 'transactions', component: Transactions },
  ];
