import { TestBed } from '@angular/core/testing';

import { BtInvoiceService } from './bt-invoice.service';

describe('BtInvoiceService', () => {
  let service: BtInvoiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtInvoiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
