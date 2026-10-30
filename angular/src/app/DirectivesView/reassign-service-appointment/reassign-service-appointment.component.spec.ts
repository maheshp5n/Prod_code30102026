import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReassignServiceAppointmentComponent } from './reassign-service-appointment.component';

describe('ReassignServiceAppointmentComponent', () => {
  let component: ReassignServiceAppointmentComponent;
  let fixture: ComponentFixture<ReassignServiceAppointmentComponent>;

  beforeEach(async(() => {
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
