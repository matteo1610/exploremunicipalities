import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatePointOfInterestComponent } from './create-point-of-interest.component';

describe('CreatePointOfInterestComponent', () => {
  let component: CreatePointOfInterestComponent;
  let fixture: ComponentFixture<CreatePointOfInterestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreatePointOfInterestComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CreatePointOfInterestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
