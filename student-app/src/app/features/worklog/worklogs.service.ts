import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  CreateWorkLogRequest,
  WorkLogResponse,
} from '../../core/models/worklog.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class WorkLogService {
  private baseUrl = `${environment.apiUrl}/worklogs`;

  constructor(private http: HttpClient) {}

  getWorkLogsByInternship(internshipId: number, page: number = 0, size: number = 10): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/internship/${internshipId}?page=${page}&size=${size}`);
  }

  createWorkLog(request: CreateWorkLogRequest): Observable<WorkLogResponse> {
    return this.http.post<WorkLogResponse>(this.baseUrl, request);
  }

  deleteWorkLog(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  updateWorkLog(id: number, request: any): Observable<WorkLogResponse> {
    return this.http.put<WorkLogResponse>(`${this.baseUrl}/${id}`, request);
  }
}