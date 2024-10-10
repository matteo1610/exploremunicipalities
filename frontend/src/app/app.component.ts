import { CommonModule } from '@angular/common'; // Importa CommonModule
import { Component } from '@angular/core';
import { provideRouter, RouterLink, RouterOutlet } from '@angular/router';
import { routes } from './app.routes'; // Importa routes
import { JwtModule } from '@auth0/angular-jwt';
import { AuthService } from './Services/auth.service';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, JwtModule, CommonModule], // Aggiungi RouterOutlet e RouterLink ai imports
  providers: [],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent {
  title = 'frontend';

  constructor(private authService: AuthService) { }

  logout() {
    this.authService.logout();
  }

  isAuthenticated(): boolean {
    return this.authService.authenticated;
  }
}
