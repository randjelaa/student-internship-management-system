import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { forkJoin } from 'rxjs';

// Material Imports
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

// Services & Models
import { InternshipsService } from '../internships/internships.service'; // Prilagodi putanju
import { Internship } from '../../core/models/internship.model'; // Prilagodi putanju

@Component({
  selector: 'app-work-log-list',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './worklog-list.component.html'
})
export class WorkLogListComponent implements OnInit {
  
  // Ovde čuvamo samo one prakse koje su prihvaćene
  acceptedInternships: Internship[] = [];
  loading = true;

  constructor(
    private internshipService: InternshipsService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadAcceptedInternships();
  }

  loadAcceptedInternships(): void {
    this.loading = true;

    // Koristimo forkJoin da bismo paralelno dobili sve prakse i statuse tvojih prijava
    forkJoin({
      allInternships: this.internshipService.getAll(),
      myApplications: this.internshipService.getMyApplications()
    }).subscribe({
      next: ({ allInternships, myApplications }) => {
        
        // 1. Mapiramo ID-eve onih praksi koje su 'ACCEPTED'
        const acceptedIds = myApplications
          .filter((app: any) => app.status === 'ACCEPTED')
          .map((app: any) => app.internshipId);

        // 2. Filtriramo listu svih praksi tako da ostanu samo te prihvaćene
        this.acceptedInternships = allInternships.filter(internship => 
          acceptedIds.includes(internship.id)
        );

        this.loading = false;
      },
      error: (err) => {
        console.error('Error loading internships:', err);
        this.loading = false;
      }
    });
  }

  viewDetails(id: number): void {
    // Navigacija na detaljan pregled dnevnika za tu praksu
    this.router.navigate(['/worklog', id]);
  }
}