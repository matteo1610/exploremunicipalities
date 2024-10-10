import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';
import { Contribution } from '../Model/contribution';

@Injectable({
  providedIn: 'root'
})
export class ContributionService {

  constructor(private httpClient: HttpClient) { }

  // Ottieni tutte le contribuzioni associate ad un punto
  public getContributionsOfPoint(pointId: number): Observable<Contribution[]> {
    return this.httpClient.get<Contribution[]>(`${environment.baseUrl}/api/v1/contributions/${pointId}`);
  }


    // Ottieni i dettagli di una singola contribuzione
    public getContributionDetail(contributionId: number): Observable<Contribution> {
      return this.httpClient.get<Contribution>(`${environment.baseUrl}/api/v1/contributions/details/${contributionId}`);
    }   

    // Crea un punto di interesse
  createPointOfInterest(requestBody: any, token: string): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    return this.httpClient.post(`${environment.baseUrl}/api/v1/contributions/pointOfInterest`, requestBody, { headers, responseType: 'text' });
  }

  
}
