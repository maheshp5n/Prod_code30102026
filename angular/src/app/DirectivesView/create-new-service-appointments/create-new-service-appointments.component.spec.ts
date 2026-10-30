import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNewServiceAppointmentsComponent } from './create-new-service-appointments.component';

describe('CreateNewServiceAppointmentsComponent', () => {
  let component: CreateNewServiceAppointmentsComponent;
  let fixture: ComponentFixture<CreateNewServiceAppointmentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewServiceAppointmentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewServiceAppointmentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
