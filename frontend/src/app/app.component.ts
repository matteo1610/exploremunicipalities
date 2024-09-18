import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common'; // Importa CommonModule
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { environment } from '../environments/environment.development';
import { MunicipalitiesService } from './municipalities.service';
import { Municipality } from './municipality';
import { MunicipalityComponent } from "./municipality/municipality.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterOutlet, MunicipalityComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent {
  title = 'frontend';
  municipalities: Municipality[] = [];

  constructor(municipalitiesService: MunicipalitiesService) {
    municipalitiesService.getMunicipalities()
    .subscribe((municipalities) => {
      this.municipalities = municipalities;
    });
  }
}
