import { TestBed } from '@angular/core/testing';

import { BtServiceOrderService } from './bt-service-order.service';

describe('BtServiceOrderService', () => {
  let service: BtServiceOrderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtServiceOrderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
