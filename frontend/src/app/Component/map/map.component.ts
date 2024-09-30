import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Point } from '../../Model/point';
import { ContributionService } from '../../contribution.service';

import * as L from 'leaflet';

@Component({
  selector: 'app-map',
  standalone: true,
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {
  @Input() points: Point[] = [];
  @Input() center: [number, number] = [0, 0];
  @Input() zoom: number = 13;
  @Output() pointSelected = new EventEmitter<Point>();

  private map: any;

  constructor(private contributionService: ContributionService) {}

  ngOnInit(): void {
    this.initMap();
    this.addPoints(this.points);
  }

  private initMap(): void {
    this.map = L.map('map').setView(this.center, this.zoom);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors'
    }).addTo(this.map);
  }

  // Metodo per aggiungere i punti alla mappa
  addPoints(points: Point[]): void {
    const customIcon = L.icon({
      iconUrl: 'assets/geo-alt-fill.svg',
      iconSize: [32, 32],
      iconAnchor: [16, 32],
      popupAnchor: [0, -32]
    });

    points.forEach(point => {
      const marker = L.marker([point.position.latitude, point.position.longitude], { icon: customIcon }).addTo(this.map);

      // Aggiunge un evento click al marker per visualizzare i contributi
      marker.on('click', () => {
        this.contributionService.getContributionsOfPoint(point.id).subscribe(contributions => {
          let popupContent = `<strong>Punto:</strong> ${point.id}`;
          if (contributions.length > 0) {
            contributions.forEach(contribution => {
              this.contributionService.getContributionDetail(contribution.id).subscribe(detail => {
                popupContent += `<br><strong>Titolo:</strong> ${detail.title}<br><strong>Descrizione:</strong> ${detail.description}`;
                marker.bindPopup(popupContent).openPopup();
              });
            });
          } else {
            popupContent += '<br>Nessun contributo disponibile';
            marker.bindPopup(popupContent).openPopup();
          }
        });
      });
    });
  }
}