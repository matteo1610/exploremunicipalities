import { Component, Input } from '@angular/core';
import { ComponentFixture } from '@angular/core/testing';
import { Municipality } from '../municipality';

@Component({
  selector: 'app-municipality',
  standalone: true,
  imports: [],
  templateUrl: './municipality.component.html',
  styleUrl: './municipality.component.css'
})
export class MunicipalityComponent {

  @Input()
  public municipality!: Municipality;
}
