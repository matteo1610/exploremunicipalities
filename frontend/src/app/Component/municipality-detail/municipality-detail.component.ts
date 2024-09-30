import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MunicipalityComponent } from '../municipality/municipality.component';
import { MunicipalitiesService } from '../../municipalities.service';
import { Point } from '../../Model/point';
import { Municipality } from '../../Model/municipality';
import { MapComponent } from '../map/map.component';
import { ContributionDetailComponent } from '../../contribution-detail/contribution-detail.component';

@Component({
  selector: 'app-municipality-detail',
  standalone: true,
  imports: [CommonModule, MunicipalityComponent, MapComponent, ContributionDetailComponent], // Use the component directly
  templateUrl: './municipality-detail.component.html',
  styleUrls: ['./municipality-detail.component.css']
})
export class MunicipalityDetailComponent implements OnInit {
  points: Point[] = []; // Array per memorizzare i punti di interesse
  hasPoints: boolean = false; // Flag per indicare se ci sono punti di interesse
  name: string = ''; // Nome del comune
  province: string = ''; // Provincia del comune
  municipality!: Municipality; // Dettagli del comune
  selectedPoint!: Point; // Punto selezionato

  constructor(
    private route: ActivatedRoute, // Servizio per gestire le route attive
    private municipalitiesService: MunicipalitiesService // Servizio per recuperare i dati dei comuni
  ) {}

  ngOnInit(): void {
    // Recupera l'ID del comune dalla route attiva
    const municipalityId = +this.route.snapshot.paramMap.get('id')!;
    console.log(`ID Comune: ${municipalityId}`);

    // Recupera i dettagli del comune
    this.municipalitiesService.getMunicipalities().subscribe(municipalities => {
      const municipality = municipalities.find(m => m.id === municipalityId);
      if (municipality) {
        this.municipality = municipality;
        this.name = municipality.name;
        this.province = municipality.province;
        this.loadPoints(municipalityId); // Carica i punti di interesse per il comune
      } else {
        this.name = 'Comune non trovato';
      }
    });
  }

  // Metodo per caricare i punti di interesse per il comune specificato
  loadPoints(municipalityId: number): void {
    this.municipalitiesService.getPointsForMunicipality(municipalityId).subscribe((points: Point[]) => {
      this.points = points;
      this.hasPoints = this.points.length > 0; // Imposta il flag se ci sono punti di interesse
    });
  }

  // Metodo per gestire l'evento del punto selezionato
  onPointSelected(point: Point): void {
    this.selectedPoint = point;
  }
}