import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

export interface FilterState {
  search: string;
  companyId: string;
  techId: string;
}

@Component({
  selector: 'app-filter-bar',
  standalone: true,
  imports: [
    CommonModule, 
    FormsModule, 
    MatFormFieldModule, 
    MatInputModule, 
    MatSelectModule, 
    MatIconModule, 
    MatButtonModule
  ],
  templateUrl: './filter-bar.component.html',
  styleUrls: ['./filter-bar.component.css']
})
export class FilterBarComponent {
  @Input() companies: any[] = [];
  @Input() technologies: any[] = [];
  
  @Output() filterChanged = new EventEmitter<FilterState>();

  search = '';
  companyFilter = '';
  technologyFilter = '';

  private searchSubject = new Subject<string>();

  constructor() {
    this.searchSubject.pipe(
      debounceTime(300),
      distinctUntilChanged()
    ).subscribe(() => this.emitFilters());
  }

  onSearchInput() {
    this.searchSubject.next(this.search);
  }

  emitFilters() {
    this.filterChanged.emit({
      search: this.search,
      companyId: this.companyFilter,
      techId: this.technologyFilter
    });
  }

  resetFilters() {
    this.search = '';
    this.companyFilter = '';
    this.technologyFilter = '';
    this.emitFilters();
  }
}