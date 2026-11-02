import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ServiceappointmentComponent } from './serviceappointment.component';

describe('ServiceappointmentComponent', () => {
  let component: ServiceappointmentComponent;
  let fixture: ComponentFixture<ServiceappointmentComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceappointmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceappointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
