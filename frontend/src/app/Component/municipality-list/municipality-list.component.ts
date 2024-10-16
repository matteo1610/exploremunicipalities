import { Component } from '@angular/core';
import { Municipality } from '../../Model/municipality';
import { MunicipalityComponent } from '../municipality/municipality.component';
import { CommonModule } from '@angular/common';
import { MunicipalitiesService } from '../../Services/municipalities.service';

@Component({
  selector: 'app-municipality-list',
  standalone: true,
  imports: [CommonModule, MunicipalityComponent],
  templateUrl: './municipality-list.component.html',
  styleUrl: './municipality-list.component.css'
})
export class MunicipalityListComponent {
  municipalities: Municipality[] = [];

  constructor(municipalitiesService: MunicipalitiesService) {
    municipalitiesService.getMunicipalities()
    .subscribe((municipalities) => {
      this.municipalities = municipalities;
    });
  }
}
