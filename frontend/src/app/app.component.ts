import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { environment } from '../environments/environment.development';
import { MunicipalitiesService } from './municipalities.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';

  constructor(municipalitiesService: MunicipalitiesService) {
    municipalitiesService.getMunicipalities().
    subscribe(municipalities => {
      console.log(municipalities);
    });
  }
}
