import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatInvoiceDetailComponent } from './bat-invoice-detail.component';

describe('BatInvoiceDetailComponent', () => {
  let component: BatInvoiceDetailComponent;
  let fixture: ComponentFixture<BatInvoiceDetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatInvoiceDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatInvoiceDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
