import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatReassignserviceappointmentComponent } from './bat-reassignserviceappointment.component';

describe('BatReassignserviceappointmentComponent', () => {
  let component: BatReassignserviceappointmentComponent;
  let fixture: ComponentFixture<BatReassignserviceappointmentComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatReassignserviceappointmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatReassignserviceappointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
