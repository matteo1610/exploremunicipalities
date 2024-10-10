import { Injectable } from '@angular/core';
import { MunicipalitiesService } from '../../municipalities.service';
import { ContributionService } from '../../contribution.service';
import { AuthService } from '../../auth.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(public auth: AuthService, public municipality: MunicipalitiesService, public contribution: ContributionService) 
  { }
}
