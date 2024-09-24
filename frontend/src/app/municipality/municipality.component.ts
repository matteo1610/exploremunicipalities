import { Component, Input } from '@angular/core';
import { ComponentFixture } from '@angular/core/testing';
import { Municipality } from '../municipality';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-municipality',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './municipality.component.html',
  styleUrl: './municipality.component.css'
})
export class MunicipalityComponent {

  @Input()
  public municipality!: Municipality;
}
