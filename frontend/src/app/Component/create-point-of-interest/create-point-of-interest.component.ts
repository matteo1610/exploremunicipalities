import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MapComponent } from '../map/map.component';
import { ContributionService } from '../../Services/contribution.service';

@Component({
  selector: 'app-create-point-of-interest',
  standalone: true,
  imports: [FormsModule, CommonModule, MapComponent],
  templateUrl: './create-point-of-interest.component.html',
  styleUrls: ['./create-point-of-interest.component.css']
})
export class CreatePointOfInterestComponent {
  position = { latitude: 0, longitude: 0 };
  request = { title: '', description: '' };
  isLoading = false; // Stato di caricamento
  message = ''; // Messaggio di successo o errore
  isSuccess = false; // Stato del messaggio di successo

  constructor(private contributionService: ContributionService, private router: Router) { }

  onCoordinatesSelected(coords: { latitude: number, longitude: number }) {
    this.position.latitude = coords.latitude;
    this.position.longitude = coords.longitude;
  }

  onSubmit() {
    if (!this.position.latitude || !this.position.longitude || !this.request.title || !this.request.description) {
      this.message = 'Tutti i campi sono obbligatori.';
      this.isSuccess = false;
      return;
    }

    this.isLoading = true;
    const requestBody = {
      position: this.position,
      request: this.request
    };

    const token = localStorage.getItem('access_token');
    if (!token) {
      this.message = 'Token di autenticazione mancante. Effettua il login.';
      this.isLoading = false;
      this.isSuccess = false;
      return;
    }

    this.contributionService.createPointOfInterest(requestBody, token).subscribe(response => {
      console.log('Point of Interest created:', response);
      this.isLoading = false;
      this.message = 'Punto di interesse creato con successo!';
      this.isSuccess = true;
      setTimeout(() => {
        this.router.navigate(['/list']);
      }, 2000);
    }, error => {
      console.error('Error creating Point of Interest:', error);
      this.isLoading = false;
      this.message = 'Errore nella creazione del punto di interesse. Riprova.';
      this.isSuccess = false;
    });
  }
}