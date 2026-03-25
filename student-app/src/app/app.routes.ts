import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { authGuard } from './core/auth/auth.guard';
import { MainLayoutComponent } from './layout/main/main-layout.component';

export const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [authGuard],
    children: [
      {
        path: 'cv',
        loadComponent: () =>
          import('./features/cv/cv.component').then((m) => m.CvComponent),
      },
      {
        path: 'internships',
        loadComponent: () =>
          import('./features/internships/internships.component').then(
            (m) => m.InternshipsComponent,
          ),
      },
      {
        path: 'internships/:id',
        loadComponent: () =>
          import('./features/internships/internship-details.component').then(
            (m) => m.InternshipDetailsComponent,
          ),
      },
      {
        path: 'worklogs', 
        loadComponent: () =>
          import('./features/worklog/worklog-list.component') 
            .then((m) => m.WorkLogListComponent),
      },
      {
        path: 'worklogs/:id', 
        loadComponent: () =>
          import('./features/worklog/worklog.component') 
            .then((m) => m.WorkLogComponent),
      },
      {
        path: '',
        redirectTo: 'cv',
        pathMatch: 'full',
      },
    ],
  },
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
];
