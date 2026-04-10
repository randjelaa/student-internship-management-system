import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { forkJoin, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import {
  MatPaginator,
  MatPaginatorModule,
  PageEvent,
} from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { InternshipsService } from '../internships/internships.service';
import { Internship } from '../../core/models/internship.model';
import { DataTableComponent } from '../shared/data-table/data-table.component';

@Component({
  selector: 'app-work-log-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatSelectModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    DataTableComponent
  ],
  templateUrl: './worklogs.component.html',
  styleUrl: './worklogs.component.css',
})
export class WorkLogListComponent implements OnInit {
  acceptedInternships: Internship[] = [];
  companies: any[] = [];
  technologies: any[] = [];
  loading = true;

  totalElements = 0;
  pageSize = 5;
  currentPage = 0;
  search = '';
  companyFilter = '';
  technologyFilter = '';

  private searchSubject = new Subject<string>();
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private internshipService: InternshipsService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.searchSubject
      .pipe(debounceTime(300), distinctUntilChanged())
      .subscribe(() => {
        this.filter();
      });

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

  onSearchInput() {
    this.searchSubject.next(this.search);
  }

  filter() {
    this.currentPage = 0;
    if (this.paginator) this.paginator.pageIndex = 0;
    this.loadData();
  }

  onPageChange(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadData();
  }

  resetFilters() {
    this.search = '';
    this.companyFilter = '';
    this.technologyFilter = '';
    this.filter();
  }

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
