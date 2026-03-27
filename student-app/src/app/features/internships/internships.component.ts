import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import {
  MatPaginator,
  MatPaginatorModule,
  PageEvent,
} from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatProgressBarModule } from '@angular/material/progress-bar';

import { InternshipsService } from './internships.service';
import { Internship } from '../../core/models/internship.model';
import { RecommendationResponse } from '../../core/models/recommendation.model';

@Component({
  selector: 'app-internships',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatButtonModule,
    MatInputModule,
    MatSelectModule,
    MatCardModule,
    MatPaginatorModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatProgressBarModule,
  ],
  templateUrl: './internships.component.html',
  styleUrl: './internships.component.css',
})
export class InternshipsComponent implements OnInit {
  internships: Internship[] = [];
  companies: any[] = [];
  technologies: any[] = [];
  recommendations: RecommendationResponse[] = [];

  totalElements = 0;
  pageSize = 5;
  currentPage = 0;
  search = '';
  companyFilter = '';
  technologyFilter = '';

  loadingRecommendations = false;
  applicationsMap: { [key: number]: any } = {};
  displayedColumns = ['title', 'company', 'status', 'actions'];

  private searchSubject = new Subject<string>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private service: InternshipsService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.searchSubject
      .pipe(debounceTime(300), distinctUntilChanged())
      .subscribe((searchValue) => {
        console.log('Searching for:', searchValue);
        this.filter();
      });

    this.loadInitialData();
  }

  loadInitialData() {
    forkJoin({
      companies: this.service.getAllCompanies(),
      technologies: this.service.getAllTechnologies(),
      applications: this.service.getMyApplications(),
      recs: this.service.getRecommendations(),
    }).subscribe(({ companies, technologies, applications, recs }) => {
      this.companies = companies;
      this.technologies = technologies;
      this.recommendations = recs;

      applications.forEach((a) => (this.applicationsMap[a.internshipId] = a));
      this.loadData();
    });
  }

  loadData() {
    const companyId = this.companyFilter ? +this.companyFilter : undefined;
    const techId = this.technologyFilter ? +this.technologyFilter : undefined;

    this.service
      .getNotAcceptedInternships(
        this.currentPage,
        this.pageSize,
        this.search,
        companyId,
        techId,
      )
      .subscribe((res) => {
        this.internships = res.content;
        this.totalElements = res.totalElements;
      });
  }

  onSearchInput() {
    this.searchSubject.next(this.search);
  }

  filter() {
    this.currentPage = 0;
    if (this.paginator) {
      this.paginator.pageIndex = 0;
    }
    this.loadData();
  }

  onPageChange(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadData();
  }

  generateAI() {
    this.loadingRecommendations = true;
    this.service.generateRecommendations().subscribe({
      next: (res) => {
        this.recommendations = res;
        this.loadingRecommendations = false;
      },
      error: () => (this.loadingRecommendations = false),
    });
  }

  resetFilters() {
    this.search = '';
    this.companyFilter = '';
    this.technologyFilter = '';
    this.filter();
  }

  openDetails(id: number) {
    this.router.navigate(['/internships', id]);
  }

  hasApplied(internshipId: number): boolean {
    return !!this.applicationsMap[internshipId];
  }

  getStatus(internshipId: number): string {
    return this.applicationsMap[internshipId]?.status || '-';
  }

  apply(id: number) {
    this.service.apply(id).subscribe({
      next: (app: any) => {
        this.applicationsMap[id] = app;
      },
      error: (err) => console.error('Application error', err),
    });
  }

  getFormattedScore(score: number): string {
    return (score * 10).toFixed(1);
  }

  getScoreColor(score: number): string {
    const val = score * 10;
    if (val >= 8) return '#2e7d32';
    if (val >= 5) return '#f9a825';
    return '#d32f2f';
  }

  goToWorkLogs(internshipId: number) {
    this.router.navigate(['/worklogs', internshipId]);
  }
}
