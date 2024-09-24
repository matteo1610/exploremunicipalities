import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MunicipalityDetailComponent } from './municipality-detail.component';

describe('MunicipalityDetailComponent', () => {
  let component: MunicipalityDetailComponent;
  let fixture: ComponentFixture<MunicipalityDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MunicipalityDetailComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MunicipalityDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
