import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtNewInvoiceComponent } from './bt-new-invoice.component';

describe('BtNewInvoiceComponent', () => {
  let component: BtNewInvoiceComponent;
  let fixture: ComponentFixture<BtNewInvoiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtNewInvoiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtNewInvoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
