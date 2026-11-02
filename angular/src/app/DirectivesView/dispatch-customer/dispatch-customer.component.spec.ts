import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { DispatchCustomerComponent } from './dispatch-customer.component';

describe('DispatchCustomerComponent', () => {
  let component: DispatchCustomerComponent;
  let fixture: ComponentFixture<DispatchCustomerComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ DispatchCustomerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DispatchCustomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
