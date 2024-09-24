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

  public getContributions(pointId: number): Observable<Contribution[]> {
    return this.httpClient.get<Contribution[]>(`${environment.baseUrl}/api/getContributions/${pointId}`);
  }
}
