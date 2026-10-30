import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCreateWccCustomerApproveComponent } from './bt-create-wcc-customer-approve.component';

describe('BtCreateWccCustomerApproveComponent', () => {
  let component: BtCreateWccCustomerApproveComponent;
  let fixture: ComponentFixture<BtCreateWccCustomerApproveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateWccCustomerApproveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateWccCustomerApproveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
