import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VendorServiceAppointmentComponent } from './vendor-service-appointment.component';

describe('VendorServiceAppointmentComponent', () => {
  let component: VendorServiceAppointmentComponent;
  let fixture: ComponentFixture<VendorServiceAppointmentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VendorServiceAppointmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VendorServiceAppointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
