import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';

@Component({
  selector: 'app-data-table',
  templateUrl: './data-table.component.html',
  styleUrl: './data-table.component.css',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatButtonModule,
    MatInputModule,
    MatSelectModule,
    MatCardModule,
    MatPaginatorModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatProgressBarModule,
  ],
})
export class DataTableComponent {
  @Input() data: any[] = [];
  @Input() displayedColumns: string[] = [];

  @Input() totalElements = 0;
  @Input() pageSize = 5;

  @Input() loading = false;

  @Input() onAction: (type: string, row: any) => void = () => {};
  @Input() getStatus: (id: number) => string = () => '-';
  @Input() hasApplied: (id: number) => boolean = () => false;

  @Output() pageChange = new EventEmitter<PageEvent>();
  @Output() rowClick = new EventEmitter<any>();

  handleAction(type: string, row: any, event: Event) {
    event.stopPropagation();
    this.onAction(type, row);
  }
}
