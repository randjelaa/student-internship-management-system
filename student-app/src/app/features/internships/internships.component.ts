import { Component, OnInit } from '@angular/core';
import { InternshipsService } from './internships.service';
import { Internship } from '../../core/models/internship.model';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { forkJoin } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-internships',
  standalone: true,
  imports: [
  FormsModule,
  MatTableModule,
  MatButtonModule,
  MatInputModule,
  MatSelectModule,
  CommonModule
],
  templateUrl: './internships.component.html',
  styleUrl: './internships.component.css'
})
export class InternshipsComponent implements OnInit {

  internships: Internship[] = [];
  filtered: Internship[] = [];

  search = '';

  companyFilter = '';
technologyFilter = '';

applicationsMap: { [key: number]: any } = {};

displayedColumns: string[] = [
  'title',
  'company',
  'location',
  'status',
  'actions'
];

companies: string[] = [];
technologies: string[] = [];

  constructor(
    private service: InternshipsService,
    private router: Router
  ) {}

  ngOnInit() {
  forkJoin({
  internships: this.service.getAll(),
  companies: this.service.getCompanies(),
  technologies: this.service.getTechnologies(),
  applications: this.service.getMyApplications()
}).subscribe(({ internships, companies, technologies, applications }) => {

  this.internships = internships;
  this.filtered = internships;

  // ✅ OVO JE KLJUČNO
  this.companies = companies.map((c: any) => c.name);
  this.technologies = technologies.map((t: any) => t.name);

  this.applicationsMap = {};
  applications.forEach(a => {
    this.applicationsMap[a.internshipId] = a;
  });
});
}

  filter() {
  this.filtered = this.internships.filter(i => {
    const matchesSearch = i.title
      .toLowerCase()
      .includes(this.search.toLowerCase());

    const matchesCompany =
      !this.companyFilter || i.companyName === this.companyFilter;

    const matchesTechnology =
      !this.technologyFilter ||
      i.technologies?.includes(this.technologyFilter);

    return matchesSearch && matchesCompany && matchesTechnology;
  });
}

  openDetails(id: number) {
    console.log('klik', id);
  this.router.navigate(['/internship-details', id]);
    this.router.navigate(['/internship-details', id]);
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
    error: err => {
      console.error('Already applied or error', err);
    }
  });
}

resetFilters() {
  this.search = '';
  this.companyFilter = '';
  this.technologyFilter = '';
  this.filtered = this.internships;
}
}