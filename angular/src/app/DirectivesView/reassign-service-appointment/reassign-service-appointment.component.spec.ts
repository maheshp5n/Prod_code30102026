import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ReassignServiceAppointmentComponent } from './reassign-service-appointment.component';

describe('ReassignServiceAppointmentComponent', () => {
  let component: ReassignServiceAppointmentComponent;
  let fixture: ComponentFixture<ReassignServiceAppointmentComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ReassignServiceAppointmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReassignServiceAppointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
