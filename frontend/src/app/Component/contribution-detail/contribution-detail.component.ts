import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { ContributionService } from '../../contribution.service';
import { Contribution } from '../../Model/contribution';
import { Point } from '../../Model/point';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-contribution-detail',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './contribution-detail.component.html',
  styleUrls: ['./contribution-detail.component.css']
})
export class ContributionDetailComponent implements OnInit, OnChanges {
  @Input() point!: Point; // Punto di interesse selezionato
  contributions: Contribution[] = []; // Contributi da visualizzare

  constructor(private contributionService: ContributionService) {}

  ngOnInit(): void {
    if (this.point) {
      this.loadContributions(this.point.id);
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['point'] && this.point) {
      this.loadContributions(this.point.id);
    }
  }

  // Metodo per caricare i contributi per il punto specificato
  loadContributions(pointId: number): void {
    this.contributionService.getContributionsOfPoint(pointId).subscribe(contributions => {
      this.contributions = contributions;
    });
  }
}