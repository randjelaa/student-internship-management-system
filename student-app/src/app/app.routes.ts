import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { authGuard } from './core/auth/auth.guard';
import { MainLayoutComponent } from './layout/main/main-layout.component';

export const routes: Routes = [
    {
    path: 'login',
    component: LoginComponent
  },
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [authGuard],
    children: [
      {
        path: 'dashboard',
        loadComponent: () =>
          import('./features/dashboard/dashboard.component')
            .then(m => m.DashboardComponent)
      },
      {
        path: 'cv',
        loadComponent: () =>
          import('./features/cv/cv.component')
            .then(m => m.CvComponent)
      },
      {
        path: 'internships',
        loadComponent: () =>
          import('./features/internships/internships.component')
            .then(m => m.InternshipsComponent)
      },
      {
        path: 'internship-details/:id',
        loadComponent: () =>
          import('./features/internships/internship-details.component')
            .then(m => m.InternshipDetailsComponent)
      },
      {
        path: 'recommendations',
        loadComponent: () =>
          import('./features/recommendations/recommendations.component')
            .then(m => m.RecommendationsComponent)
      },
      {
  path: 'worklog', // Ovo će sada biti lista svih prihvaćenih praksi
  loadComponent: () =>
    import('./features/worklog/worklog-list.component') // Nova komponenta sa karticama
      .then(m => m.WorkLogListComponent)
},
{
  path: 'worklog/:id', // Ovo je stranica gde se zapravo kuca dnevnik za određenu praksu
  loadComponent: () =>
    import('./features/worklog/worklog.component') // Postojeća komponenta sa formom i listom logova
      .then(m => m.WorkLogComponent)
},
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      }
    ]
  },
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  }
];
