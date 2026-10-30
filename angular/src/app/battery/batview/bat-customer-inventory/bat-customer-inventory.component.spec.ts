import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatCustomerInventoryComponent } from './bat-customer-inventory.component';

describe('BatCustomerInventoryComponent', () => {
  let component: BatCustomerInventoryComponent;
  let fixture: ComponentFixture<BatCustomerInventoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatCustomerInventoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatCustomerInventoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
