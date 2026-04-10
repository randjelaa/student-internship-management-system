import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';

import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { InternshipsService } from '../internships/internships.service';
import { Internship } from '../../core/models/internship.model';
import { DataTableComponent } from '../shared/data-table/data-table.component';
import { FilterBarComponent, FilterState } from '../shared/filter-bar/filter-bar.component';

@Component({
  selector: 'app-work-log-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatButtonModule,
    MatIconModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    DataTableComponent,
    FilterBarComponent,
  ],
  templateUrl: './worklogs.component.html',
  styleUrl: './worklogs.component.css',
})
export class WorkLogListComponent implements OnInit {

  acceptedInternships: Internship[] = [];
  companies: any[] = [];
  technologies: any[] = [];

  totalElements = 0;
  pageSize = 5;
  currentPage = 0;

  search = '';
  companyFilter = '';
  technologyFilter = '';

  loading = true;

  constructor(
    private internshipService: InternshipsService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.loadInitialData();
  }

  loadInitialData() {
    this.loading = true;

    forkJoin({
      companies: this.internshipService.getAllCompanies(),
      technologies: this.internshipService.getAllTechnologies(),
    }).subscribe(({ companies, technologies }) => {
      this.companies = companies;
      this.technologies = technologies;
      this.loadData();
    });
  }

  loadData() {
    this.loading = true;

    const companyId = this.companyFilter ? +this.companyFilter : undefined;
    const techId = this.technologyFilter ? +this.technologyFilter : undefined;

    this.internshipService
      .getAcceptedInternships(
        this.currentPage,
        this.pageSize,
        this.search,
        companyId,
        techId,
      )
      .subscribe({
        next: (res) => {
          this.acceptedInternships = res.content;
          this.totalElements = res.totalElements;
          this.loading = false;
        },
        error: (err) => {
          console.error('Error loading accepted internships', err);
          this.loading = false;
        },
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
  openDetails(id: number): void {
    this.router.navigate(['/internships', id]);
  }

  goToWorkLogs(id: number) {
    this.router.navigate(['/worklogs', id]);
  }

  handleAction = (type: string, row: any) => {
    if (type === 'manageLogs') {
      this.goToWorkLogs(row.id);
    }
  };
}