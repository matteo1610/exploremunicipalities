import { Component, OnInit } from '@angular/core';
import { Municipality } from '../municipality';
import { Point } from '../point';
import { ActivatedRoute } from '@angular/router';
import { MunicipalitiesService } from '../municipalities.service';

@Component({
  selector: 'app-municipality-detail',
  standalone: true,
  imports: [],
  templateUrl: './municipality-detail.component.html',
  styleUrl: './municipality-detail.component.css'
})
export class MunicipalityDetailComponent implements OnInit {
  points: Point[] = []; // Array per memorizzare i punti

  constructor(
    private route: ActivatedRoute,
    private municipalitiesService: MunicipalitiesService // Usa il tuo servizio esistente
  ) {}

  ngOnInit(): void {
    const municipalityId = +this.route.snapshot.paramMap.get('id')!; // Ottieni l'ID del comune

    // Chiamata al servizio per ottenere i punti
    this.municipalitiesService.getPointsForMunicipality(municipalityId)
      .subscribe((points) => {
        this.points = points; // Assegna i dati ai punti
      });
  }
}