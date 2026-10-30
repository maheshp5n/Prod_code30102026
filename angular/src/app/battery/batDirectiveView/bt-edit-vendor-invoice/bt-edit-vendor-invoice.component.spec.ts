import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtEditVendorInvoiceComponent } from './bt-edit-vendor-invoice.component';

describe('BtEditVendorInvoiceComponent', () => {
  let component: BtEditVendorInvoiceComponent;
  let fixture: ComponentFixture<BtEditVendorInvoiceComponent>;

  beforeEach(async(() => {
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
