import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatInvoiceDetailComponent } from './bat-invoice-detail.component';

describe('BatInvoiceDetailComponent', () => {
  let component: BatInvoiceDetailComponent;
  let fixture: ComponentFixture<BatInvoiceDetailComponent>;

  beforeEach(async(() => {
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
