import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatWccCustomerComponent } from './bat-wcc-customer.component';

describe('BatWccCustomerComponent', () => {
  let component: BatWccCustomerComponent;
  let fixture: ComponentFixture<BatWccCustomerComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatWccCustomerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatWccCustomerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
