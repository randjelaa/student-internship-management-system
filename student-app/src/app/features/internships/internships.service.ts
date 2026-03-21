import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Internship

 } from '../../core/models/internship.model';
@Injectable({ providedIn: 'root' })
export class InternshipsService {

  private baseUrl = 'http://localhost:8080/api/internships';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Internship[]> {
    return this.http.get<Internship[]>(this.baseUrl);
  }

  getById(id: number): Observable<Internship> {
    return this.http.get<Internship>(`${this.baseUrl}/${id}`);
  }

  apply(internshipId: number, studentId: number) {
    return this.http.post('http://localhost:8080/api/applications', {
      internshipId,
      studentId
    });
  }
}