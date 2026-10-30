import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DispatchCustomerComponent } from './dispatch-customer.component';

describe('DispatchCustomerComponent', () => {
  let component: DispatchCustomerComponent;
  let fixture: ComponentFixture<DispatchCustomerComponent>;

  beforeEach(async(() => {
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
