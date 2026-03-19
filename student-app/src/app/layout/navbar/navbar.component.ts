import { Component, computed } from '@angular/core';
import { AuthService } from '../../core/auth/auth.service';
import { Router } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    MatToolbarModule,
    MatButtonModule
  ],
  templateUrl: './navbar.component.html'
})
export class NavbarComponent {

  user = computed(() => this.authService.getUser());

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  logout() {
    this.authService.logout().subscribe(() => {
      this.router.navigate(['/login']);
    });
  }
}