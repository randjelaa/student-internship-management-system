import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Internship } from '../../core/models/internship.model';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class InternshipsService {
  private baseUrlInternships = `${environment.apiUrl}/internships`;
  private baseUrlApplications = `${environment.apiUrl}/applications`;
  private baseUrlCompanies = `${environment.apiUrl}/companies`;
  private baseUrlTechnologies = `${environment.apiUrl}/technologies`;
  private baseUrlRecommendations = `${environment.apiUrl}/recommendations`;

  constructor(private http: HttpClient) {}

  getAllInternships(
    page: number,
    size: number,
    search: string = '',
    companyId?: number,
    techId?: number,
  ): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('search', search);

    if (companyId) params = params.set('company', companyId.toString());
    if (techId) params = params.set('technology', techId.toString());

    return this.http.get<any>(this.baseUrlInternships, { params });
  }

  getInternshipById(id: number): Observable<Internship> {
    return this.http.get<Internship>(`${this.baseUrlInternships}/${id}`);
  }

  apply(internshipId: number) {
    return this.http.post(`${this.baseUrlApplications}`, {
      internshipId,
    });
  }

  getMyApplications() {
    return this.http.get<any[]>(`${this.baseUrlApplications}/my`);
  }

  getAllCompanies() {
    return this.http.get<string[]>(`${this.baseUrlCompanies}`);
  }

  getAllTechnologies() {
    return this.http.get<string[]>(`${this.baseUrlTechnologies}`);
  }

  generateRecommendations() {
    return this.http.post<any[]>(`${this.baseUrlRecommendations}/generate`, {});
  }

  getRecommendations() {
    return this.http.get<any[]>(`${this.baseUrlRecommendations}`);
  }

  getNotAcceptedInternships(
    page: number,
    size: number,
    search: string = '',
    companyId?: number,
    techId?: number,
  ): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('search', search);

    if (companyId) params = params.set('company', companyId.toString());
    if (techId) params = params.set('tech', techId.toString()); 

    return this.http.get<any>(`${this.baseUrlInternships}/not-accepted`, { params });
  }

  getAcceptedInternships(
    page: number,
    size: number,
    search: string = '',
    companyId?: number,
    techId?: number,
  ): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('search', search);

    if (companyId) params = params.set('company', companyId.toString());
    if (techId) params = params.set('tech', techId.toString());

    return this.http.get<any>(`${this.baseUrlInternships}/accepted`, { params });
  }
}
