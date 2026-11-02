import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { EngineerRatingComponent } from './engineer-rating.component';

describe('EngineerRatingComponent', () => {
  let component: EngineerRatingComponent;
  let fixture: ComponentFixture<EngineerRatingComponent>;

  beforeEach(waitForAsync(() => {
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
