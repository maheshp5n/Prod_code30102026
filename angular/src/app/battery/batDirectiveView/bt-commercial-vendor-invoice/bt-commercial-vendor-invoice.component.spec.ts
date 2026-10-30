import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCommercialVendorInvoiceComponent } from './bt-commercial-vendor-invoice.component';

describe('BtCommercialVendorInvoiceComponent', () => {
  let component: BtCommercialVendorInvoiceComponent;
  let fixture: ComponentFixture<BtCommercialVendorInvoiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCommercialVendorInvoiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCommercialVendorInvoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
