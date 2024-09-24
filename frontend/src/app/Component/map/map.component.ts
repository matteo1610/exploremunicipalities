import { CommonModule } from '@angular/common'; // Importa CommonModule
import { Component } from '@angular/core';
import * as L from 'leaflet';

@Component({
  selector: 'app-map',
  standalone: true, // Rendi il componente standalone
  imports: [CommonModule], // Importa CommonModule
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent {
  map!: L.Map;

  ngOnInit(): void {
    this.initMap();
  }

  initMap(): void {
    this.map = L.map('map').setView([51.505, -0.09], 13); // Coordinate di Londra
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '© OpenStreetMap'
    }).addTo(this.map);
    
    L.marker([51.5, -0.09]).addTo(this.map)
      .bindPopup('Un popup CSS3 personalizzabile.')
      .openPopup();
  }
}
