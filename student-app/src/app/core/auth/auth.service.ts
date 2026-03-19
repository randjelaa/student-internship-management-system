import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';
import { tap } from 'rxjs';
import { switchMap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private userSignal = signal<User | null>(null);

  constructor(private http: HttpClient) {}

  login(email: string, password: string) {
  return this.http.post('http://localhost:8080/api/auth/login', {
    email,
    password
  }).pipe(
    tap(() => console.log('Login OK')),
    switchMap(() => this.fetchMe())
  );
}

  fetchMe() {
    return this.http.get<User>('http://localhost:8080/api/auth/me')
      .pipe(
        tap(user => this.userSignal.set(user))
      );
  }

  logout() {
    return this.http.post('/api/auth/logout', {})
      .pipe(
        tap(() => this.userSignal.set(null))
      );
  }

  getUser() {
    return this.userSignal();
  }

  isLoggedIn() {
    return !!this.userSignal();
  }
}