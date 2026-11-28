import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtCreateExpenseserviceappointmentComponent } from './bt-create-expenseserviceappointment.component';

describe('BtCreateExpenseserviceappointmentComponent', () => {
  let component: BtCreateExpenseserviceappointmentComponent;
  let fixture: ComponentFixture<BtCreateExpenseserviceappointmentComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateExpenseserviceappointmentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateExpenseserviceappointmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
