import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { forkJoin } from 'rxjs';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { InternshipsService } from '../internships/internships.service';
import { Internship } from '../../core/models/internship.model';

@Component({
  selector: 'app-work-log-list',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule, MatIconModule],
  templateUrl: './worklog-list.component.html',
  styleUrl: './worklog-list.component.css',
})
export class WorkLogListComponent implements OnInit {
  acceptedInternships: Internship[] = [];
  loading = true;

  constructor(
    private internshipService: InternshipsService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.loadAcceptedInternships();
  }

  loadAcceptedInternships(): void {
    this.loading = true;

    forkJoin({
      allInternships: this.internshipService.getAllInternships(),
      myApplications: this.internshipService.getMyApplications(),
    }).subscribe({
      next: ({ allInternships, myApplications }) => {
        const acceptedIds = myApplications
          .filter((app: any) => app.status === 'ACCEPTED')
          .map((app: any) => app.internshipId);

        this.acceptedInternships = allInternships.filter((internship) =>
          acceptedIds.includes(internship.id),
        );

        this.loading = false;
      },
      error: (err) => {
        console.error('Error loading internships:', err);
        this.loading = false;
      },
    });
  }

  viewDetails(id: number): void {
    this.router.navigate(['/worklogs', id]);
  }
}
