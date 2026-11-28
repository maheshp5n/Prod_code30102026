import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CommercialVendorInvoiceComponent } from './commercial-vendor-invoice.component';

describe('CommercialVendorInvoiceComponent', () => {
  let component: CommercialVendorInvoiceComponent;
  let fixture: ComponentFixture<CommercialVendorInvoiceComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CommercialVendorInvoiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommercialVendorInvoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
