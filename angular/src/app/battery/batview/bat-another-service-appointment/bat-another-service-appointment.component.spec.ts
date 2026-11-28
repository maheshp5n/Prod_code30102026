import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatAnotherServiceAppointmentComponent } from './bat-another-service-appointment.component';

describe('BatAnotherServiceAppointmentComponent', () => {
  let component: BatAnotherServiceAppointmentComponent;
  let fixture: ComponentFixture<BatAnotherServiceAppointmentComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatAnotherServiceAppointmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatAnotherServiceAppointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
