import { Injectable } from '@angular/core';
import { AuthService } from '../../auth.service';
import { MunicipalitiesService } from '../municipalities.service';
import { ContributionService } from '../contribution.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(public auth: AuthService, public municipality: MunicipalitiesService, public contribution: ContributionService) 
  { }
}
