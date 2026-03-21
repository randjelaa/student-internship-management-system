import { Component, OnInit } from '@angular/core';
import { InternshipsService } from './internships.service';
import { Internship } from '../../core/models/internship.model';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-internships',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './internships.component.html',
  styleUrl: './internships.component.css'
})
export class InternshipsComponent implements OnInit {

  internships: Internship[] = [];
  filtered: Internship[] = [];

  search = '';

  companyFilter = '';
technologyFilter = '';

  constructor(
    private service: InternshipsService,
    private router: Router
  ) {}

  ngOnInit() {
    this.service.getAll().subscribe(data => {
      this.internships = data;
      this.filtered = data;
    });
  }

  filter() {
  this.filtered = this.internships.filter(i =>
    i.title.toLowerCase().includes(this.search.toLowerCase()) &&
    (!this.companyFilter || i.companyName === this.companyFilter) &&
    (!this.technologyFilter || i.technologies.includes(this.technologyFilter))
  );
}

  openDetails(id: number) {
    console.log('klik', id);
  this.router.navigate(['/internship-details', id]);
    this.router.navigate(['/internship-details', id]);
  }
}