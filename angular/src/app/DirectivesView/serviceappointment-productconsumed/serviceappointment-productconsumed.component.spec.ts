import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiceappointmentProductconsumedComponent } from './serviceappointment-productconsumed.component';

describe('ServiceappointmentProductconsumedComponent', () => {
  let component: ServiceappointmentProductconsumedComponent;
  let fixture: ComponentFixture<ServiceappointmentProductconsumedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceappointmentProductconsumedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceappointmentProductconsumedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
