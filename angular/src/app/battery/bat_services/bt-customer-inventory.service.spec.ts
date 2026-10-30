import { TestBed } from '@angular/core/testing';

import { BtCustomerInventoryService } from './bt-customer-inventory.service';

describe('BtCustomerInventoryService', () => {
  let service: BtCustomerInventoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtCustomerInventoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
