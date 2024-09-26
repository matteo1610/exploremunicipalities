import { CommonModule } from '@angular/common'; // Importa CommonModule
import { Component } from '@angular/core';
import * as L from 'leaflet';
import { MunicipalityDetailComponent } from '../municipality-detail/municipality-detail.component';

@Component({
  selector: 'app-map',
  standalone: true, // Rendi il componente standalone
  imports: [CommonModule, MunicipalityDetailComponent], // Importa CommonModule
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent {
  map!: L.Map;
  

}
