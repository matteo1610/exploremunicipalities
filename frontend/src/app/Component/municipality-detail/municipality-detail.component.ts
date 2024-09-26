import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MunicipalitiesService } from '../../municipalities.service';
import { Point } from '../../Model/point';
import { Municipality } from '../../Model/municipality';
import * as L from 'leaflet';
import { CommonModule } from '@angular/common';
import { MunicipalityComponent } from '../municipality/municipality.component';
import { ContributionService } from '../../contribution.service';
import { Contribution } from '../../Model/contribution';

@Component({
  selector: 'app-municipality-detail',
  standalone: true,
  imports: [CommonModule, MunicipalityComponent],
  templateUrl: './municipality-detail.component.html',
  styleUrls: ['./municipality-detail.component.css']
})
export class MunicipalityDetailComponent implements OnInit {
  points: Point[] = [];
  map!: L.Map;
  hasPoints: boolean = false;
  message: string = '';
  name: string = '';
  province: string = '';
  municipality!: Municipality;

  constructor(
    private route: ActivatedRoute,
    private municipalitiesService: MunicipalitiesService,
    private contributionService: ContributionService
  ) {}

  ngOnInit(): void {
    const municipalityId = +this.route.snapshot.paramMap.get('id')!;
    console.log(`ID Comune: ${municipalityId}`);

    // Ottieni i dettagli del comune
    this.municipalitiesService.getMunicipalities().subscribe(municipalities => {
      const municipality = municipalities.find(m => m.id === municipalityId);
      if (municipality) {
        this.municipality = municipality;
        this.name = municipality.name;
        this.province = municipality.province;
        this.loadPoints(municipalityId);
      } else {
        this.name = 'Comune non trovato';
      }
    });
  }

  loadPoints(municipalityId: number): void {
    // Ottieni i punti associati al comune
    this.municipalitiesService.getPointsForMunicipality(municipalityId).subscribe((points: Point[]) => {
      this.points = points;

      if (this.points.length > 0) {
        this.hasPoints = true;
        this.initMapWithPoints();
      } else {
        this.hasPoints = false;
        this.initMapCenteredOnMunicipality();
        this.message = "Non ci sono punti presenti nella piattaforma per il comune selezionato.";
      }
    });
  }

  initMapWithPoints(): void {
    // Inizializza la mappa con i punti se ci sono punti disponibili
    this.map = L.map('map').setView([this.points[0].position.latitude, this.points[0].position.longitude], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap contributors'
    }).addTo(this.map);

    const customIcon = L.icon({
      iconUrl: 'assets/geo-alt-fill.svg', 
      iconSize: [32, 32],
      iconAnchor: [16, 32],
      popupAnchor: [0, -32]
    });

    this.points.forEach(point => {
      const marker = L.marker([point.position.latitude, point.position.longitude], { icon: customIcon }).addTo(this.map);

      // Ottieni i contributi per ogni punto
      this.contributionService.getContributionsOfPoint(point.id).subscribe(contributions => {
        console.log('Contributions for point', point.id, contributions); // Debug
        point.contributions = contributions;

        // Verifica se ci sono contributi
        let popupContent = `Punto: ${point.id}`;
        if (contributions && contributions.length > 0) {
          const contributionDetails = contributions.map(contribution => 
            this.contributionService.getContributionDetail(contribution.id).toPromise()
          );

          Promise.all(contributionDetails).then(contributions => {
            contributions.forEach(contributions => {
              popupContent += `<br><strong>Titolo:</strong> ${contributions?.title}<br><strong>Descrizione:</strong> ${contributions?.description}`;
            });
            marker.bindPopup(popupContent);
          });
        } else {
          popupContent += '<br>Nessun contributo disponibile';
          marker.bindPopup(popupContent);
        }
      });
    });
  }

  initMapCenteredOnMunicipality(): void {
    // Inizializza la mappa centrata sull'identityPoint del comune se non ci sono punti per una vista generale
    if (this.municipality && this.municipality.identityPoint) {
      this.map = L.map('map').setView([this.municipality.identityPoint.latitude, this.municipality.identityPoint.longitude], 13);

      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors'
      }).addTo(this.map);
    }
  }

  createPopupContent(point: Point): string {
    // Crea il contenuto del popup per un punto
    let popupContent = `Punto: ${point.id}`;
    
    if (point.contributions && point.contributions.length > 0) {
      point.contributions.forEach((contribution: Contribution) => {
        popupContent += `<br><strong>Title:</strong> ${contribution.title}<br><strong>Description:</strong> ${contribution.type}`;
      });
    } else {
      popupContent += '<br>Nessun contributo disponibile';
    }

    return popupContent;
  }
}
