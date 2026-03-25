import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CvResponse } from '../../core/models/cv.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class CvService {
  private baseUrl = `${environment.apiUrl}/cv`;

  constructor(private http: HttpClient) {}

  getCv(): Observable<CvResponse> {
    return this.http.get<CvResponse>(`${this.baseUrl}`);
  }

  createCv(body: any) {
    return this.http.post(`${this.baseUrl}`, body);
  }

  updateCv(body: any) {
    return this.http.put(`${this.baseUrl}`, body);
  }

  deleteCv() {
    return this.http.delete(`${this.baseUrl}`);
  }

  downloadPdf() {
    return this.http.get(`${this.baseUrl}/pdf`, {
      responseType: 'blob',
    });
  }

  uploadImage(formData: FormData) {
    return this.http.post(`${this.baseUrl}/upload-image`, formData, {
      responseType: 'text',
    });
  }
}
