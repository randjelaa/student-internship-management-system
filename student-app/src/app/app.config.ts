import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { credentialsInterceptor } from './core/interceptors/credentials.interceptor';
import { APP_INITIALIZER } from '@angular/core';
import { AuthService } from './core/auth/auth.service';

function initializeAuth(authService: AuthService) {
  return () => authService.checkAuth();
}

export const appConfig: ApplicationConfig = {
  providers: 
  [ provideRouter(routes), 
    provideAnimationsAsync('noop'), 
    provideHttpClient(withInterceptors([credentialsInterceptor])),
    {
      provide: APP_INITIALIZER,
      useFactory: initializeAuth,
      deps: [AuthService],
      multi: true
    }
  ]
};
