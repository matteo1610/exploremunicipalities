import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Municipality } from './Model/municipality';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment.development';
import { Point } from './Model/point';

@Injectable({
  providedIn: 'root'
})
export class MunicipalitiesService {

  constructor(private httpClient: HttpClient) { }

   public getMunicipalities(): Observable<Municipality[]> {
    return this.httpClient.get<Municipality[]>(`${environment.baseUrl}/api/v1/municipalities`);
}

public getPointsForMunicipality(municipalityId: number): Observable<Point[]> {
  return this.httpClient.get<Point[]>(`${environment.baseUrl}/api/v1/points/${municipalityId}`);
}
}
