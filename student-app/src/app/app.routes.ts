import { Routes } from '@angular/router';

import { LoginComponent } from './features/login/login.component';
import { CvComponent } from './features/cv/cv.component';
import { InternshipsComponent } from './features/internships/internships.component';
import { InternshipDetailsComponent } from './features/internships/internship-details.component';
import { WorkLogListComponent } from './features/worklog/worklogs.component';
import { WorkLogComponent } from './features/worklog/worklogs-details.component';

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
        component: CvComponent,
      },
      {
        path: 'internships',
        component: InternshipsComponent,
      },
      {
        path: 'internships/:id',
        component: InternshipDetailsComponent,
      },
      {
        path: 'worklogs',
        component: WorkLogListComponent,
      },
      {
        path: 'worklogs/:id',
        component: WorkLogComponent,
      },
      {
        path: '',
        redirectTo: 'cv',
        pathMatch: 'full',
      },
    ],
  },

  {
    path: '**',
    redirectTo: 'login',
  },
];