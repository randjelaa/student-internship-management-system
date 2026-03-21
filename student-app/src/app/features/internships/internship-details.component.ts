import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { InternshipsService } from './internships.service';
import { AuthService } from '../../core/auth/auth.service';

@Component({
  selector: 'app-internship-details',
  standalone: true,
  templateUrl: './internship-details.component.html'
})
export class InternshipDetailsComponent implements OnInit {

  internship: any;

  constructor(
    private route: ActivatedRoute,
    private service: InternshipsService,
    private auth: AuthService
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.service.getById(id).subscribe(data => {
      this.internship = data;
    });
  }

  apply() {
  const user = this.auth.getUser();

  if (!user) {
    alert('You must be logged in');
    return;
  }

  this.service.apply(this.internship.id, user.id)
    .subscribe({
      next: () => alert('Applied!'),
      error: () => alert('Already applied or error')
    });
}
}