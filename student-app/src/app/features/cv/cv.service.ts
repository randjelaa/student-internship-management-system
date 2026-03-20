import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CvResponse } from '../../core/models/cv.model';

@Injectable({ providedIn: 'root' })
export class CvService {

  private baseUrl = 'http://localhost:8080/api/students';

  constructor(private http: HttpClient) {}

  getCv(studentId: number): Observable<CvResponse> {
    return this.http.get<CvResponse>(`${this.baseUrl}/${studentId}/cv`);
  }

  createCv(studentId: number, body: any) {
    return this.http.post(`${this.baseUrl}/${studentId}/cv`, body);
  }

  updateCv(studentId: number, body: any) {
    return this.http.put(`${this.baseUrl}/${studentId}/cv`, body);
  }

  downloadPdf(studentId: number) {
    return this.http.get(`${this.baseUrl}/${studentId}/cv/pdf`, {
      responseType: 'blob'
    });
  }
}