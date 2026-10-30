import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EngineerRatingComponent } from './engineer-rating.component';

describe('EngineerRatingComponent', () => {
  let component: EngineerRatingComponent;
  let fixture: ComponentFixture<EngineerRatingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EngineerRatingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EngineerRatingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
