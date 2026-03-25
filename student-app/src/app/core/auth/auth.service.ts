import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';
import { tap } from 'rxjs';
import { switchMap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { catchError, of, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = `${environment.apiUrl}/auth`;
  
  private userSignal = signal<User | null>(null);
  private isAuthenticating = signal<boolean>(true);

  constructor(private http: HttpClient) {}

  checkAuth(): Observable<User | null> {
    return this.http.get<User>(`${this.baseUrl}/me`).pipe(
      tap({
        next: (user) => {
          this.userSignal.set(user);
          this.isAuthenticating.set(false);
        },
        error: () => {
          this.userSignal.set(null);
          this.isAuthenticating.set(false);
        },
      }),
      catchError(() => {
        this.userSignal.set(null);
        this.isAuthenticating.set(false);
        return of(null);
      }),
    );
  }

  login(email: string, password: string) {
    return this.http
      .post(`${this.baseUrl}/login`, {
        email,
        password,
      })
      .pipe(
        tap(() => console.log('Login OK')),
        switchMap(() => this.fetchMe()),
      );
  }

  fetchMe() {
    return this.http
      .get<User>(`${this.baseUrl}/me`)
      .pipe(tap((user) => this.userSignal.set(user)));
  }

  logout() {
    return this.http
      .post(`${this.baseUrl}/logout`, {})
      .pipe(tap(() => this.userSignal.set(null)));
  }

  getUser() {
    return this.userSignal();
  }

  isLoggedIn() {
    return !!this.userSignal();
  }
}
