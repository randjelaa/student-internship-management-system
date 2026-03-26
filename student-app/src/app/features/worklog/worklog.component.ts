import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterModule } from '@angular/router';

// Material
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

// Services
import { WorkLogService } from './worklog.service';
import { InternshipsService } from '../internships/internships.service';

@Component({
  selector: 'app-worklog',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatIconModule,
    MatPaginatorModule,
    MatTableModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatTableModule,
    MatPaginatorModule,
  ],
  templateUrl: './worklog.component.html',
  styleUrl: './worklog.component.css',
})
export class WorkLogComponent implements OnInit {
  internshipId!: number;
  internship: any = null;
  logs: any[] = [];
  loading = false;
  editingLogId: number | null = null; // Prati da li editujemo

  totalElements = 0;
  page = 0;
  size = 5;

  newLog = { startDate: null, endDate: null, description: '' };

  constructor(
    private route: ActivatedRoute,
    private service: WorkLogService,
    private internshipService: InternshipsService,
    private snackBar: MatSnackBar,
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.internshipId = +id;
      this.loadInitialData();
    }
  }

  private loadInitialData() {
    this.internshipService
      .getInternshipById(this.internshipId)
      .subscribe((data) => (this.internship = data));
    this.loadLogs();
  }

  loadLogs() {
    this.loading = true;
    this.service
      .getWorkLogsByInternship(this.internshipId, this.page, this.size)
      .subscribe({
        next: (res) => {
          this.logs = res.content;
          this.totalElements = res.totalElements;
          this.loading = false;
        },
        error: () => {
          this.showMsg('Error loading logs');
          this.loading = false;
        },
      });
  }

  // Poziva se kada klikneš na ikonicu olovke u tabeli
  prepareEdit(log: any) {
    this.editingLogId = log.id;
    this.newLog = {
      startDate: log.startDate,
      endDate: log.endDate,
      description: log.description,
    };
    // Skroluj do forme
    document
      .querySelector('.form-section')
      ?.scrollIntoView({ behavior: 'smooth' });
  }

  saveLog() {
    if (!this.isValid()) {
      this.showMsg('Please fill all fields');
      return;
    }

    const payload = {
      internshipId: this.internshipId,
      startDate: this.formatDate(this.newLog.startDate),
      endDate: this.formatDate(this.newLog.endDate),
      description: this.newLog.description,
    };

    if (this.editingLogId) {
      // UPDATE režim
      this.service.updateWorkLog(this.editingLogId, payload).subscribe({
        next: () => {
          this.showMsg('Log updated');
          this.resetForm();
          this.loadLogs();
        },
        error: () => this.showMsg('Update failed'),
      });
    } else {
      // CREATE režim
      this.service.createWorkLog(payload).subscribe({
        next: () => {
          this.showMsg('Log saved');
          this.resetForm();
          this.loadLogs();
        },
        error: () => this.showMsg('Save failed'),
      });
    }
  }

  deleteLog(id: number) {
    if (confirm('Delete this entry?')) {
      this.service.deleteWorkLog(id).subscribe(() => {
        this.showMsg('Deleted');
        this.loadLogs();
      });
    }
  }

  resetForm() {
    this.editingLogId = null;
    this.newLog = { startDate: null, endDate: null, description: '' };
  }

  onPageChange(event: PageEvent) {
    this.page = event.pageIndex;
    this.size = event.pageSize;
    this.loadLogs();
  }

  private isValid() {
    return !!(
      this.newLog.startDate &&
      this.newLog.endDate &&
      this.newLog.description.trim()
    );
  }
  private showMsg(msg: string) {
    this.snackBar.open(msg, 'OK', { duration: 3000 });
  }
  private formatDate(date: any) {
    return date ? new Date(date).toISOString().split('T')[0] : '';
  }
}
