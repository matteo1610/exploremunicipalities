import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Municipality } from './municipality';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class MunicipalitiesService {

  constructor(private httpClient: HttpClient) { }

   public getMunicipalities(): Observable<Municipality[]> {
    return this.httpClient.get<Municipality[]>(`${environment.baseUrl}/api/municipalities/getMunicipalities`);
}
}
