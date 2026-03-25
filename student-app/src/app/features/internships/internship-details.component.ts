import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { ActivatedRoute } from '@angular/router';
import { InternshipsService } from './internships.service';
import { AuthService } from '../../core/auth/auth.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-internship-details',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule],
  templateUrl: './internship-details.component.html',
})

export class InternshipDetailsComponent implements OnInit {
  internship: any;
  applicationsMap: { [key: number]: any } = {}; 

  constructor(
    private route: ActivatedRoute,
    private service: InternshipsService,
    private auth: AuthService,
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    forkJoin({
      internship: this.service.getInternshipById(id),
      applications: this.service.getMyApplications(),
    }).subscribe(({ internship, applications }) => {
      this.internship = internship;
      this.applicationsMap = {};
      applications.forEach((a) => {
        this.applicationsMap[a.internshipId] = a;
      });
    });
  }

  hasApplied(internshipId: number): boolean {
    return !!this.applicationsMap[internshipId];
  }

  apply() {
    const user = this.auth.getUser();
    if (!user) {
      alert('You must be logged in');
      return;
    }

    this.service.apply(this.internship.id).subscribe({
      next: (app: any) => {
        this.applicationsMap[this.internship.id] = app;
        alert('Applied!');
      },
      error: () => alert('Already applied or error'),
    });
  }
}
