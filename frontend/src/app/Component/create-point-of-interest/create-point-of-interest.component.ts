import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MapComponent } from '../map/map.component';
import { ContributionService } from '../../Services/contribution.service';
import { User } from '../../Model/user';

@Component({
  selector: 'app-create-point-of-interest',
  standalone: true,
  imports: [FormsModule, CommonModule, MapComponent],
  templateUrl: './create-point-of-interest.component.html',
  styleUrls: ['./create-point-of-interest.component.css']
})
export class CreatePointOfInterestComponent {
  
}