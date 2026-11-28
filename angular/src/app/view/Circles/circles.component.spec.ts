import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CirclesComponent } from './circles.component';

describe('CirclesComponent', () => {
  let component: CirclesComponent;
  let fixture: ComponentFixture<CirclesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CirclesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CirclesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
