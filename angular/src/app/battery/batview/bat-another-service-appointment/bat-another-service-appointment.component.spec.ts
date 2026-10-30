import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatAnotherServiceAppointmentComponent } from './bat-another-service-appointment.component';

describe('BatAnotherServiceAppointmentComponent', () => {
  let component: BatAnotherServiceAppointmentComponent;
  let fixture: ComponentFixture<BatAnotherServiceAppointmentComponent>;

  beforeEach(async(() => {
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
