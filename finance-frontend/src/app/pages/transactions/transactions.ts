import { Component, OnInit, signal } from '@angular/core';
import { CommonModule, DatePipe }     from '@angular/common';
import { FormsModule }                from '@angular/forms';
import {
  TransactionService,
  TransactionResponse,
  TransactionRequest,
} from '../../services/transaction';
import {
  CategoryService,
  CategoryResponse,
} from '../../services/category';

@Component({
  selector:    'app-transactions',
  standalone:  true,
  imports:     [CommonModule, FormsModule, DatePipe],
  templateUrl: './transactions.html',
  styleUrl:    './transactions.css',
})
export class Transactions implements OnInit {

  // ── State (signals for reactive template bindings) ────────────────────
  transactions  = signal<TransactionResponse[]>([]);
  categories    = signal<CategoryResponse[]>([]);
  isLoading     = signal(false);
  currentPage   = signal(0);
  totalPages    = signal(0);
  totalElements = signal(0);

  readonly pageSize = 15;

  // ── Filters (plain props — compatible with ngModel) ───────────────────
  filterType      = '';
  filterCategory  = '';
  filterStartDate = '';
  filterEndDate   = '';

  // ── Modal state ───────────────────────────────────────────────────────
  showModal         = false;
  showDeleteConfirm = false;
  isEditing         = false;
  editingId:  number | null = null;
  deletingId: number | null = null;

  // ── Form (plain object for ngModel two-way binding) ───────────────────
  form: TransactionRequest = {
    description: '',
    amount:      0,
    type:        'EXPENSE',
    date:        '',
    categoryId:  null,
    notes:       null,
  };

  constructor(
    private transactionService: TransactionService,
    private categoryService:    CategoryService,
  ) {}

  ngOnInit() {
    this.loadCategories();
    this.loadTransactions();
  }

  // ── Computed helpers ─────────────────────────────────────────────────

  get filteredCategories(): CategoryResponse[] {
    return this.categories().filter(c => c.type === this.form.type);
  }

  get pages(): number[] {
    return Array.from({ length: this.totalPages() }, (_, i) => i);
  }

  showPage(p: number): boolean {
    const total = this.totalPages();
    const curr  = this.currentPage();
    return (
      total <= 7 ||
      p === 0    ||
      p === total - 1 ||
      (p >= curr - 1 && p <= curr + 1)
    );
  }

  // ── Data loading ──────────────────────────────────────────────────────

  loadCategories() {
    this.categoryService.getAll()
      .subscribe(res => this.categories.set(res.categories));
  }

  loadTransactions(page = 0) {
    this.isLoading.set(true);
    this.transactionService.getAll({
      type:       this.filterType      || undefined,
      categoryId: this.filterCategory  ? +this.filterCategory : undefined,
      startDate:  this.filterStartDate || undefined,
      endDate:    this.filterEndDate   || undefined,
      page,
      size:       this.pageSize,
    }).subscribe(res => {
      this.transactions.set(res.transactions);
      this.currentPage.set(res.currentPage);
      this.totalPages.set(res.totalPages);
      this.totalElements.set(res.totalElements);
      this.isLoading.set(false);
    });
  }

  onFilterChange() { this.loadTransactions(0); }

  get hasActiveFilters(): boolean {
    return !!(this.filterType || this.filterCategory || this.filterStartDate || this.filterEndDate);
  }

  clearFilters() {
    this.filterType      = '';
    this.filterCategory  = '';
    this.filterStartDate = '';
    this.filterEndDate   = '';
    this.loadTransactions(0);
  }

  // ── Modal controls ────────────────────────────────────────────────────

  openAdd() {
    this.isEditing = false;
    this.editingId = null;
    this.form = {
      description: '',
      amount:      0,
      type:        'EXPENSE',
      date:        new Date().toISOString().split('T')[0],
      categoryId:  null,
      notes:       null,
    };
    this.showModal = true;
  }

  openEdit(t: TransactionResponse) {
    this.isEditing = true;
    this.editingId = t.id;
    const match = this.categories().find(c => c.name === t.categoryName);
    this.form = {
      description: t.description,
      amount:      t.amount,
      type:        t.type as 'INCOME' | 'EXPENSE',
      date:        t.date,
      categoryId:  match?.id ?? null,
      notes:       t.notes,
    };
    this.showModal = true;
  }

  closeModal() { this.showModal = false; }

  submitForm() {
    if (this.isEditing && this.editingId !== null) {
      this.transactionService.update(this.editingId, this.form)
        .subscribe(() => {
          this.closeModal();
          this.loadTransactions(this.currentPage());
        });
    } else {
      this.transactionService.create(this.form)
        .subscribe(() => {
          this.closeModal();
          this.loadTransactions(0);
        });
    }
  }

  // ── Delete flow ───────────────────────────────────────────────────────

  confirmDelete(id: number) {
    this.deletingId = id;
    this.showDeleteConfirm = true;
  }

  cancelDelete() {
    this.deletingId = null;
    this.showDeleteConfirm = false;
  }

  doDelete() {
    if (this.deletingId === null) return;
    this.transactionService.delete(this.deletingId)
      .subscribe(() => {
        this.cancelDelete();
        this.loadTransactions(this.currentPage());
      });
  }

  // ── Form helpers ──────────────────────────────────────────────────────

  onTypeChange() {
    this.form.categoryId = null;
  }
}
