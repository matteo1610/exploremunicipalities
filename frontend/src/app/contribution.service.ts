import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Contribution } from './Model/contribution';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class ContributionService {

  constructor(private httpClient: HttpClient) { }

  // Ottieni tutte le contribuzioni associate ad un punto
  public getContributionsOfPoint(pointId: number): Observable<Contribution[]> {
    return this.httpClient.get<Contribution[]>(`${environment.baseUrl}/api/contributions/getContributions/${pointId}`);
  }


    // Ottieni i dettagli di una singola contribuzione
    public getContributionDetail(contributionId: number): Observable<Contribution> {
      return this.httpClient.get<Contribution>(`${environment.baseUrl}/api/contributions/getContributionDetail/${contributionId}`);
    }
   
    public addPointOfInterest(userId: string, contribution: Contribution): Observable<Contribution> {
      return this.httpClient.post<Contribution>(`${environment.baseUrl}/api/contributions/createPointOfInterest/${userId}`, contribution);
    }
}
