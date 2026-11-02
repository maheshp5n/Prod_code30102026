import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatBoardingdetailsComponent } from './bat-boardingdetails.component';

describe('BatBoardingdetailsComponent', () => {
  let component: BatBoardingdetailsComponent;
  let fixture: ComponentFixture<BatBoardingdetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatBoardingdetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatBoardingdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
