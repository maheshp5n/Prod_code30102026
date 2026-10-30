import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VendorServiceAppointmentDetailComponent } from './vendor-service-appointment-detail.component';

describe('VendorServiceAppointmentDetailComponent', () => {
  let component: VendorServiceAppointmentDetailComponent;
  let fixture: ComponentFixture<VendorServiceAppointmentDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VendorServiceAppointmentDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VendorServiceAppointmentDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
