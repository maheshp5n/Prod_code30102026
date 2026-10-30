import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCustomerOutComponent } from './bt-customer-out.component';

describe('BtCustomerOutComponent', () => {
  let component: BtCustomerOutComponent;
  let fixture: ComponentFixture<BtCustomerOutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCustomerOutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCustomerOutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
