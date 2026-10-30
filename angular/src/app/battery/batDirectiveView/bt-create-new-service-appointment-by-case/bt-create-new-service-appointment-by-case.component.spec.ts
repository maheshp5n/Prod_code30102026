import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCreateNewServiceAppointmentByCaseComponent } from './bt-create-new-service-appointment-by-case.component';

describe('BtCreateNewServiceAppointmentByCaseComponent', () => {
  let component: BtCreateNewServiceAppointmentByCaseComponent;
  let fixture: ComponentFixture<BtCreateNewServiceAppointmentByCaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateNewServiceAppointmentByCaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateNewServiceAppointmentByCaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
