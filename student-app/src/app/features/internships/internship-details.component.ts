import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common'; // Dodat Location
import { ActivatedRoute, RouterModule } from '@angular/router';
import { forkJoin } from 'rxjs';

import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { InternshipsService } from './internships.service';
import { AuthService } from '../../core/auth/auth.service';

@Component({
  selector: 'app-internship-details',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    MatProgressSpinnerModule,
    RouterModule,
  ],
  templateUrl: './internship-details.component.html',
  styleUrl: './internship-details.component.css',
})
export class InternshipDetailsComponent implements OnInit {
  internship: any;
  applicationsMap: { [key: number]: any } = {};

  constructor(
    private route: ActivatedRoute,
    private service: InternshipsService,
    private auth: AuthService,
    private location: Location // Injectovan servis
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    forkJoin({
      internship: this.service.getInternshipById(id),
      applications: this.service.getMyApplications(),
    }).subscribe({
      next: ({ internship, applications }) => {
        this.internship = internship;
        applications.forEach((a) => {
          this.applicationsMap[a.internshipId] = a;
        });
      },
      error: () => this.goBack() // Ako staž ne postoji, vrati nazad
    });
  }

  goBack(): void {
    this.location.back();
  }

  hasApplied(internshipId: number): boolean {
    return !!this.applicationsMap[internshipId];
  }

  apply() {
    if (!this.auth.isLoggedIn()) {
      alert('You must be logged in');
      return;
    }

    this.service.apply(this.internship.id).subscribe({
      next: (app: any) => {
        this.applicationsMap[this.internship.id] = app;
      },
      error: () => alert('Error during application'),
    });
  }
}