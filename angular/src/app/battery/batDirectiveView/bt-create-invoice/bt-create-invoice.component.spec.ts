import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCreateInvoiceComponent } from './bt-create-invoice.component';

describe('BtCreateInvoiceComponent', () => {
  let component: BtCreateInvoiceComponent;
  let fixture: ComponentFixture<BtCreateInvoiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateInvoiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateInvoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
