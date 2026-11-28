import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatCustomerInventoryInDetailComponent } from './bat-customer-inventory-in-detail.component';

describe('BatCustomerInventoryInDetailComponent', () => {
  let component: BatCustomerInventoryInDetailComponent;
  let fixture: ComponentFixture<BatCustomerInventoryInDetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatCustomerInventoryInDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatCustomerInventoryInDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
