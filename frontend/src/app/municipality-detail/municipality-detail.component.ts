import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MunicipalitiesService } from '../municipalities.service';
import { Point } from '../point';
import * as L from 'leaflet';
import { CommonModule } from '@angular/common';
import { MunicipalityComponent } from '../municipality/municipality.component';

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
    private municipalitiesService: MunicipalitiesService
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
      L.marker([point.position.latitude, point.position.longitude], { icon: customIcon }).addTo(this.map)
        .bindPopup(`Point ID: ${point.id}`)
        .openPopup();
    });
  }
}