import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';

import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatProgressBarModule } from '@angular/material/progress-bar';

import { InternshipsService } from './internships.service';
import { Internship } from '../../core/models/internship.model';
import { RecommendationResponse } from '../../core/models/recommendation.model';
import { DataTableComponent } from '../shared/data-table/data-table.component';
import { FilterBarComponent, FilterState } from '../shared/filter-bar/filter-bar.component';

@Component({
  selector: 'app-internships',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatButtonModule,
    MatCardModule,
    MatPaginatorModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatProgressBarModule,
    DataTableComponent,
    FilterBarComponent,
  ],
  templateUrl: './internships.component.html',
  styleUrl: './internships.component.css',
})
export class InternshipsComponent implements OnInit {

  internships: Internship[] = [];
  companies: any[] = [];
  technologies: any[] = [];
  recommendations: RecommendationResponse[] = [];

  applicationsMap: { [key: number]: any } = {};

  totalElements = 0;
  pageSize = 5;
  currentPage = 0;

  search = '';
  companyFilter = '';
  technologyFilter = '';

  loadingRecommendations = false;

  constructor(
    private service: InternshipsService,
    private router: Router,
  ) {}

  ngOnInit() {
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

      applications.forEach(
        (a) => (this.applicationsMap[a.internshipId] = a),
      );

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

  //filter + pagination
  onFilterChanged(filters: FilterState) {
    this.search = filters.search;
    this.companyFilter = filters.companyId;
    this.technologyFilter = filters.techId;

    this.currentPage = 0;
    this.loadData();
  }

  onPageChange(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadData();
  }

  //actions
  openDetails(id: number) {
    this.router.navigate(['/internships', id]);
  }

  goToWorkLogs(internshipId: number) {
    this.router.navigate(['/worklogs', internshipId]);
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

  apply(id: number) {
    this.service.apply(id).subscribe({
      next: (app: any) => {
        this.applicationsMap[id] = app;
      },
      error: (err) => console.error('Application error', err),
    });
  }

  handleAction = (type: string, row: any) => {
    if (type === 'apply') {
      this.apply(row.id);
    }

    if (type === 'manageLogs') {
      this.goToWorkLogs(row.id);
    }
  };

  //status
  getStatus = (internshipId: number): string => {
    return this.applicationsMap[internshipId]?.status || '-';
  };

  hasApplied = (internshipId: number): boolean => {
    return !!this.applicationsMap[internshipId];
  };

  //ui
  getFormattedScore(score: number): string {
    return (score * 10).toFixed(1);
  }

  getScoreColor(score: number): string {
    const val = score * 10;
    if (val >= 8) return '#2e7d32';
    if (val >= 5) return '#f9a825';
    return '#d32f2f';
  }
}