import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ServiceappointmentindetailsComponent } from './serviceappointmentindetails.component';

describe('ServiceappointmentindetailsComponent', () => {
  let component: ServiceappointmentindetailsComponent;
  let fixture: ComponentFixture<ServiceappointmentindetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ServiceappointmentindetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServiceappointmentindetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
