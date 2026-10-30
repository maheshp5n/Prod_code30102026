import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCustomerInComponent } from './bt-customer-in.component';

describe('BtCustomerInComponent', () => {
  let component: BtCustomerInComponent;
  let fixture: ComponentFixture<BtCustomerInComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCustomerInComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCustomerInComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
