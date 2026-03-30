import {
  Component, OnInit, signal, AfterViewInit,
  ViewChild, ElementRef
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TransactionService, TransactionResponse } from '../../services/transaction';
import { SummaryService, MonthlySummary } from '../../services/summary';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.html',
})
export class Dashboard implements OnInit, AfterViewInit {
  summary      = signal<MonthlySummary | null>(null);
  transactions = signal<TransactionResponse[]>([]);

  // Plain strings — compatible with [(ngModel)]
  startDate = '2026-01-22';
  endDate   = '2026-02-21';

  @ViewChild('donutChart') donutChartRef!: ElementRef;
  @ViewChild('pieChart')   pieChartRef!:   ElementRef;
  @ViewChild('barChart')   barChartRef!:   ElementRef;

  donutChart: any;
  pieChart:   any;
  barChart:   any;

  constructor(
    private summaryService:     SummaryService,
    private transactionService: TransactionService,
  ) {}

  ngOnInit()       { this.loadData(); }
  ngAfterViewInit() {
    setTimeout(() => { this.initCharts(); this.updateCharts(); }, 500);
  }

  loadData() {
    this.summaryService.getSummary(this.startDate, this.endDate)
      .subscribe(data => {
        this.summary.set(data);
        setTimeout(() => this.updateCharts(), 100);
      });

    this.transactionService.getAll({
      startDate: this.startDate,
      endDate:   this.endDate,
      size:      1000,
    }).subscribe(data => {
      this.transactions.set(data.transactions);
      setTimeout(() => this.updateCharts(), 100);
    });
  }

  onDateChange() { this.loadData(); }

  get incomeTransactions()  {
    return this.transactions().filter(t => t.type?.toUpperCase() === 'INCOME');
  }
  get expenseTransactions() {
    return this.transactions().filter(t => t.type?.toUpperCase() === 'EXPENSE');
  }

  get percentageSpent() {
    const s = this.summary();
    if (!s || s.totalIncome === 0) return 0;
    return Math.min(100, Math.round((s.totalExpense / s.totalIncome) * 100));
  }

  get expensesByCategory() {
    const map = new Map<string, number>();
    this.expenseTransactions.forEach(t => {
      const cat = t.categoryName ?? 'Uncategorized';
      map.set(cat, (map.get(cat) ?? 0) + t.amount);
    });
    return map;
  }

  // ─── Charts ───────────────────────────────────────────────────────────

  initCharts() {
    const grid = 'rgba(110,110,200,0.07)';
    const tick  = '#40405e';

    this.donutChart = new Chart(this.donutChartRef.nativeElement, {
      type: 'doughnut',
      data: {
        datasets: [{
          data: [0, 100],
          backgroundColor: ['#fb7185', 'rgba(110,110,200,0.07)'],
          borderWidth: 0,
          hoverBorderWidth: 0,
        }],
      },
      options: {
        cutout: '78%',
        plugins: { legend: { display: false }, tooltip: { enabled: false } },
      },
    });

    this.pieChart = new Chart(this.pieChartRef.nativeElement, {
      type: 'pie',
      data: { labels: [], datasets: [{ data: [], backgroundColor: [], borderWidth: 0 }] },
      options: {
        plugins: {
          legend: {
            position: 'right',
            labels: {
              color: '#8080a8',
              font: { size: 11, family: 'DM Sans' },
              padding: 12,
              boxWidth: 10,
              borderRadius: 3,
            },
          },
        },
      },
    });

    this.barChart = new Chart(this.barChartRef.nativeElement, {
      type: 'bar',
      data: {
        labels: ['Income', 'Expense'],
        datasets: [{
          data: [0, 0],
          backgroundColor: ['rgba(74,222,128,0.1)', 'rgba(251,113,133,0.1)'],
          borderColor:      ['#4ade80', '#fb7185'],
          borderWidth: 2,
          borderRadius: 8,
          borderSkipped: false,
        }],
      },
      options: {
        plugins: { legend: { display: false } },
        scales: {
          y: {
            ticks: { color: tick, font: { size: 11, family: 'Space Mono' } },
            grid:  { color: grid },
            border: { display: false },
          },
          x: {
            ticks: { color: tick, font: { size: 11, family: 'DM Sans' } },
            grid:  { display: false },
            border: { display: false },
          },
        },
      },
    });
  }

  updateCharts() {
    const s = this.summary();

    if (this.donutChart) {
      const pct = this.percentageSpent;
      this.donutChart.data.datasets[0].data = [pct, Math.max(0, 100 - pct)];
      this.donutChart.update();
    }

    if (this.barChart && s) {
      this.barChart.data.datasets[0].data = [s.totalIncome, s.totalExpense];
      this.barChart.update();
    }

    if (this.pieChart) {
      const catMap = this.expensesByCategory;
      const palette = [
        '#818cf8','#4ade80','#fb7185','#d4a853',
        '#a78bfa','#38bdf8','#f97316','#34d399','#e879f9','#a3e635',
      ];
      const keys = Array.from(catMap.keys());
      this.pieChart.data.labels                          = keys;
      this.pieChart.data.datasets[0].data               = Array.from(catMap.values());
      this.pieChart.data.datasets[0].backgroundColor    = palette.slice(0, keys.length).map(c => c + 'bb');
      (this.pieChart.data.datasets[0] as any).borderColor = palette.slice(0, keys.length);
      (this.pieChart.data.datasets[0] as any).borderWidth = 1;
      this.pieChart.update();
    }
  }
}
