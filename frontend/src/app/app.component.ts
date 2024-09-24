import { CommonModule } from '@angular/common'; // Importa CommonModule
import { Component } from '@angular/core';
import { provideRouter, RouterLink, RouterOutlet } from '@angular/router';
import { routes } from './app.routes'; // Importa routes


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  providers: [],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})

export class AppComponent {
  title = 'frontend';
  
}
