import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { WorkLogService } from './worklog.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { InternshipsService } from '../internships/internships.service';

@Component({
  selector: 'app-worklog',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatExpansionModule,
    MatIconModule,
    MatPaginatorModule,
    RouterModule
  ],
  templateUrl: './worklog.component.html',
})
export class WorkLogComponent implements OnInit {
  internshipId!: number;
  internship: any = null;
  logs: any[] = [];
  loading = false;

  totalElements = 0;
  page = 0;
  size = 5;

  newLog: any = {
    startDate: null,
    endDate: null,
    description: '',
  };

  editingLogId: number | null = null;
  editLogData: any = { startDate: null, endDate: null, description: '' };

  constructor(
    private route: ActivatedRoute,
    private service: WorkLogService,
    private internshipService: InternshipsService,
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.internshipId = +id;
      this.loadInternshipDetails();
      this.loadLogs();
    }
  }

  loadLogs() {
    this.loading = true;
    this.service
      .getWorkLogsByInternship(this.internshipId, this.page, this.size)
      .subscribe({
        next: (response) => {
          this.logs = response.content;
          this.totalElements = response.totalElements;
          this.loading = false;
        },
        error: () => (this.loading = false),
      });
  }

  onPageChange(event: PageEvent) {
    this.page = event.pageIndex;
    this.size = event.pageSize;
    this.loadLogs();
  }

  loadInternshipDetails() {
    this.internshipService
      .getInternshipById(this.internshipId)
      .subscribe((data) => {
        this.internship = data;
      });
  }

  onStartDateChange() {
    if (
      this.newLog.startDate &&
      this.newLog.endDate &&
      this.newLog.endDate < this.newLog.startDate
    ) {
      this.newLog.endDate = null;
    }
  }

  saveLog() {
    if (
      !this.newLog.startDate ||
      !this.newLog.endDate ||
      !this.newLog.description
    ) {
      alert('Please fill all fields');
      return;
    }

    const payload = {
      internshipId: this.internshipId,
      startDate: this.formatDate(this.newLog.startDate),
      endDate: this.formatDate(this.newLog.endDate),
      description: this.newLog.description,
    };

    this.service.createWorkLog(payload).subscribe({
      next: () => {
        this.newLog = { startDate: null, endDate: null, description: '' };
        this.loadLogs();
      },
      error: (err) => console.error('Save failed', err),
    });
  }

  deleteLog(id: number) {
    if (confirm('Are you sure you want to delete this log?')) {
      this.service.deleteWorkLog(id).subscribe(() => this.loadLogs());
    }
  }

  private formatDate(date: any): string {
    const d = new Date(date);
    const year = d.getFullYear();
    const month = ('0' + (d.getMonth() + 1)).slice(-2);
    const day = ('0' + d.getDate()).slice(-2);
    return `${year}-${month}-${day}`;
  }

  startEdit(log: any) {
    this.editingLogId = log.id;
    this.editLogData = {
      startDate: new Date(log.startDate),
      endDate: new Date(log.endDate),
      description: log.description,
    };
  }

  cancelEdit() {
    this.editingLogId = null;
  }

  saveUpdate() {
    if (!this.editingLogId) return;

    const payload = {
      startDate: this.formatDate(this.editLogData.startDate),
      endDate: this.formatDate(this.editLogData.endDate),
      description: this.editLogData.description,
    };

    this.service.updateWorkLog(this.editingLogId, payload).subscribe({
      next: () => {
        this.editingLogId = null;
        this.loadLogs();
      },
      error: (err) => console.error('Update failed', err),
    });
  }
}
