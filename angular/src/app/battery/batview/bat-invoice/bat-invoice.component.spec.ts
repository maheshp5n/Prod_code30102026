import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatInvoiceComponent } from './bat-invoice.component';

describe('BatInvoiceComponent', () => {
  let component: BatInvoiceComponent;
  let fixture: ComponentFixture<BatInvoiceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatInvoiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatInvoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
