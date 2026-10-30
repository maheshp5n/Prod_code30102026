import { TestBed } from '@angular/core/testing';

import { BtWccCustomerService } from './bt-wcc-customer.service';

describe('BtWccCustomerService', () => {
  let service: BtWccCustomerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtWccCustomerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
