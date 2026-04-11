import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterModule } from '@angular/router';

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

import { WorkLogService } from './worklogs.service';
import { InternshipsService } from '../internships/internships.service';
import { DataTableComponent } from '../shared/data-table/data-table.component';

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
    DataTableComponent
  ],
  templateUrl: './worklogs-details.component.html',
  styleUrl: './worklogs-details.component.css',
})
export class WorkLogComponent implements OnInit {
  internshipId!: number;

  internship: any = null;
  logs: any[] = [];

  totalElements = 0;
  page = 0;
  size = 5;

  editingLogId: number | null = null;
  newLog = {
    startDate: null,
    endDate: null,
    description: '',
  };

  constructor(
    private route: ActivatedRoute,
    private service: WorkLogService,
    private internshipService: InternshipsService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) return;

    this.internshipId = +id;
    this.loadInitialData();
  }

  private loadInitialData() {
    this.internshipService
      .getInternshipById(this.internshipId)
      .subscribe((data) => (this.internship = data));

    this.loadLogs();
  }

  loadLogs() {
    this.service
      .getMyWorkLogsByInternship(this.internshipId, this.page, this.size)
      .subscribe({
        next: (res) => {
          this.logs = res.content;
          this.totalElements = res.totalElements;
        },
        error: () => this.showMsg('Error loading logs'),
      });
  }

  saveLog() {
    if (!this.isValid()) {
      this.showMsg('Please fill all fields');
      return;
    }

    const payload = this.buildPayload();

    const request = this.editingLogId
      ? this.service.updateWorkLog(this.editingLogId, payload)
      : this.service.createWorkLog(payload);

    request.subscribe({
      next: () => {
        this.showMsg(this.editingLogId ? 'Log updated' : 'Log saved');
        this.resetForm();
        this.loadLogs();
      },
      error: () =>
        this.showMsg(this.editingLogId ? 'Update failed' : 'Save failed'),
    });
  }

  prepareEdit(log: any) {
    this.editingLogId = log.id;
    this.newLog = {
      startDate: log.startDate,
      endDate: log.endDate,
      description: log.description,
    };

    document
      .querySelector('.form-section')
      ?.scrollIntoView({ behavior: 'smooth' });
  }

  deleteLog(id: number) {
    if (!confirm('Delete this entry?')) return;

    this.service.deleteWorkLog(id).subscribe(() => {
      this.showMsg('Deleted');
      this.loadLogs();
    });
  }

  resetForm() {
    this.editingLogId = null;
    this.newLog = {
      startDate: null,
      endDate: null,
      description: '',
    };
  }

  onPageChange(event: PageEvent) {
    this.page = event.pageIndex;
    this.size = event.pageSize;
    this.loadLogs();
  }

  private isValid(): boolean {
    return !!(
      this.newLog.startDate &&
      this.newLog.endDate &&
      this.newLog.description.trim()
    );
  }

  private buildPayload() {
    return {
      internshipId: this.internshipId,
      startDate: this.formatDate(this.newLog.startDate),
      endDate: this.formatDate(this.newLog.endDate),
      description: this.newLog.description,
    };
  }

  private formatDate(date: any): string {
    return date ? new Date(date).toISOString().split('T')[0] : '';
  }

  private showMsg(msg: string) {
    this.snackBar.open(msg, 'OK', { duration: 3000 });
  }

  handleAction = (type: string, row: any) => {
    if (type === 'edit') this.prepareEdit(row);
    if (type === 'delete') this.deleteLog(row.id);
  };
}
