import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtCreateNewServiceAppointmentComponent } from './bt-create-new-service-appointment.component';

describe('BtCreateNewServiceAppointmentComponent', () => {
  let component: BtCreateNewServiceAppointmentComponent;
  let fixture: ComponentFixture<BtCreateNewServiceAppointmentComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateNewServiceAppointmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateNewServiceAppointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
