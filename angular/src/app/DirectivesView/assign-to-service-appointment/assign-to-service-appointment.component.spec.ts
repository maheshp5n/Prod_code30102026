import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AssignToServiceAppointmentComponent } from './assign-to-service-appointment.component';

describe('AssignToServiceAppointmentComponent', () => {
  let component: AssignToServiceAppointmentComponent;
  let fixture: ComponentFixture<AssignToServiceAppointmentComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignToServiceAppointmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignToServiceAppointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
