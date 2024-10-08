import { HttpClient } from '@angular/common/http';
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
    public createPointOfInterest(userId: number, requestBody: { position: { latitude: number, longitude: number }, request: { title: string, description: string } }): Observable<string> {
      return this.httpClient.post(`${environment.baseUrl}/api/v1/contributions/pointOfInterest/${userId}`, requestBody, { responseType: 'text' });
    }

  
}
