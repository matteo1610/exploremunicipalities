import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [[CommonModule, RouterModule]],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'] // Correct path 
})
export class HomeComponent {
  
}