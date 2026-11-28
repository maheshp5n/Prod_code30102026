import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatInvoiceComponent } from './bat-invoice.component';

describe('BatInvoiceComponent', () => {
  let component: BatInvoiceComponent;
  let fixture: ComponentFixture<BatInvoiceComponent>;

  beforeEach(waitForAsync(() => {
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
