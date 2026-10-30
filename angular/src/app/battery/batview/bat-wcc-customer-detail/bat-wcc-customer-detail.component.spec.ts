import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatWccCustomerDetailComponent } from './bat-wcc-customer-detail.component';

describe('BatWccCustomerDetailComponent', () => {
  let component: BatWccCustomerDetailComponent;
  let fixture: ComponentFixture<BatWccCustomerDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatWccCustomerDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatWccCustomerDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
