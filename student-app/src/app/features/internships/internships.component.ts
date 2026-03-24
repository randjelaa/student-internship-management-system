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
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-internships',
  standalone: true,
  imports: [
  FormsModule,
  MatTableModule,
  MatButtonModule,
  MatInputModule,
  MatSelectModule,
  CommonModule,
  MatCardModule
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
  'actions',
  'workLog'
];

companies: string[] = [];
technologies: string[] = [];

recommendations: any[] = [];
loadingRecommendations = false;

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
this.service.getRecommendations().subscribe({
    next: (res) => this.recommendations = res,
    error: (err) => console.error('Greška pri dobavljanju preporuka', err)
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

generateAI() {
  this.loadingRecommendations = true;
  this.service.generateRecommendations().subscribe({
    next: (res) => {
      this.recommendations = res;
      this.loadingRecommendations = false;
    },
    error: (err) => {
      console.error('AI Error', err);
      this.loadingRecommendations = false;
    }
  });
}

// Score 0.85 -> 8.5
getFormattedScore(score: number): string {
  return (score * 10).toFixed(1);
}

// Boja na osnovu ocene
getScoreColor(score: number): string {
  const val = score * 10;
  if (val >= 8) return '#2e7d32'; // Zelena
  if (val >= 5) return '#f9a825'; // Žuta/Narandžasta
  return '#d32f2f'; // Crvena
}

// Dodaj 'workLog' u displayedColumns niz
// U InternshipsComponent.ts
goToWorkLogs(internshipId: number) {
  this.router.navigate(['/worklog', internshipId]);
}
}