import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Municipality } from './municipality';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment.development';
import { Point } from './point';

@Injectable({
  providedIn: 'root'
})
export class MunicipalitiesService {

  constructor(private httpClient: HttpClient) { }

   public getMunicipalities(): Observable<Municipality[]> {
    return this.httpClient.get<Municipality[]>(`${environment.baseUrl}/api/municipalities/getMunicipalities`);
}

public getPointsForMunicipality(municipalityId: number): Observable<Point[]> {
  return this.httpClient.get<Point[]>(`${environment.baseUrl}/api/points/getPoints/${municipalityId}`);
}
}
