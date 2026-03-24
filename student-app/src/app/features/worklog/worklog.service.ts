import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface WorkLogResponse {
  id: number;
  studentId: number;
  internshipId: number;
  startDate: string;
  endDate: string;
  description: string;
}

export interface CreateWorkLogRequest {
  internshipId: number;
  startDate: string;
  endDate: string;
  description: string;
}

@Injectable({
  providedIn: 'root'
})
export class WorkLogService {
  private apiUrl = 'http://localhost:8080/api/worklogs'; // Prilagodi svom portu

  constructor(private http: HttpClient) {}

  // Dohvata sve logove ulogovanog studenta
  getMyWorkLogs(): Observable<WorkLogResponse[]> {
    return this.http.get<WorkLogResponse[]>(`${this.apiUrl}/my`);
  }

  // Kreira novi log
  createWorkLog(request: CreateWorkLogRequest): Observable<WorkLogResponse> {
    return this.http.post<WorkLogResponse>(this.apiUrl, request);
  }

  // Briše log
  deleteWorkLog(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Ažurira postojeći log
  updateWorkLog(id: number, request: any): Observable<WorkLogResponse> {
    return this.http.put<WorkLogResponse>(`${this.apiUrl}/${id}`, request);
  }
}