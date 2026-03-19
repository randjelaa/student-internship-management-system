import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatListModule } from '@angular/material/list';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterModule, MatListModule],
  templateUrl: './sidebar.component.html'
})
export class SidebarComponent {}