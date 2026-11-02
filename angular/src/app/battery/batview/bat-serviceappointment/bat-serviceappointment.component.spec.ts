import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatServiceappointmentComponent } from './bat-serviceappointment.component';

describe('BatServiceappointmentComponent', () => {
  let component: BatServiceappointmentComponent;
  let fixture: ComponentFixture<BatServiceappointmentComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatServiceappointmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatServiceappointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
