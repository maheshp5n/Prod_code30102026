import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtEditVendorInvoiceComponent } from './bt-edit-vendor-invoice.component';

describe('BtEditVendorInvoiceComponent', () => {
  let component: BtEditVendorInvoiceComponent;
  let fixture: ComponentFixture<BtEditVendorInvoiceComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtEditVendorInvoiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtEditVendorInvoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
