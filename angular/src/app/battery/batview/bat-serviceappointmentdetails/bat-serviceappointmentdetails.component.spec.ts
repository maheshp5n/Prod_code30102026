import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatServiceappointmentdetailsComponent } from './bat-serviceappointmentdetails.component';

describe('BatServiceappointmentdetailsComponent', () => {
  let component: BatServiceappointmentdetailsComponent;
  let fixture: ComponentFixture<BatServiceappointmentdetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatServiceappointmentdetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatServiceappointmentdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
