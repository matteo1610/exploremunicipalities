import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MunicipalitiesService } from '../../municipalities.service';
import { Point } from '../../Model/point';
import * as L from 'leaflet';
import { CommonModule } from '@angular/common';
import { MunicipalityComponent } from '../municipality/municipality.component';
import { ContributionService } from '../../contribution.service';

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

  constructor(
    private route: ActivatedRoute,
    private municipalitiesService: MunicipalitiesService,
    private contributionService: ContributionService
  ) {}

  ngOnInit(): void {
    const municipalityId = +this.route.snapshot.paramMap.get('id')!;
    console.log(`ID Comune: ${municipalityId}`);

    // Ottieni la lista dei comuni
    this.municipalitiesService.getMunicipalities().subscribe(municipalities => {
      // Trova il comune con l'ID corrispondente
      const municipality = municipalities.find(m => m.id === municipalityId);
      if (municipality) {
        this.name = municipality.name;
        this.province = municipality.province;
      } else {
        this.name = 'Comune non trovato';
      }
    });

    this.municipalitiesService.getPointsForMunicipality(municipalityId).subscribe((points) => {
      this.points = points;

      if (this.points.length > 0) {
        this.hasPoints = true;
        this.initMap();
      } else {
        this.message = "Non ci sono punti presenti nella piattaforma per il comune selezionato.";
        this.hasPoints = false;
      }
    });
  }

  initMap(): void {
    this.map = L.map('map').setView([this.points[0].position.latitude, this.points[0].position.longitude], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap contributors'
    }).addTo(this.map);

    // Crea un'icona personalizzata
    const customIcon = L.icon({
      iconUrl: 'assets/custom-marker-icon.png', // Percorso all'immagine dell'icona
      iconSize: [32, 32], // Dimensioni dell'icona
      iconAnchor: [16, 32], // Punto dell'icona che corrisponde alla posizione del marker
      popupAnchor: [0, -32] // Punto da cui si apre il popup rispetto all'icona
    });

    this.points.forEach(point => {
      const marker = L.marker([point.position.latitude, point.position.longitude], { icon: customIcon }).addTo(this.map);

      // Aggiungi i dettagli del contributo al popup
      let popupContent = `Point ID: ${point.id}`;
      if (point.contributions && point.contributions.length > 0) {
        point.contributions.forEach(contribution => {
          popupContent += `<br>Title: ${contribution.title}<br>Description: ${contribution.description}<br>Author: ${contribution.author.name}`;
          contribution.multimedia.forEach(media => {
            popupContent += `<br><img src="${media}" alt="Multimedia" style="width:100px;height:auto;">`;
          });
        });
      }

      marker.bindPopup(popupContent).openPopup();
    });
  }
}