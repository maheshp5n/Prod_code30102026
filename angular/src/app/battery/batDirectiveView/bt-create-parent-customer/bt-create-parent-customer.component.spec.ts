import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCreateParentCustomerComponent } from './bt-create-parent-customer.component';

describe('BtCreateParentCustomerComponent', () => {
  let component: BtCreateParentCustomerComponent;
  let fixture: ComponentFixture<BtCreateParentCustomerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateParentCustomerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateParentCustomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
