import { TestBed } from '@angular/core/testing';

import { BtServiceOrderReturnService } from './bt-service-order-return.service';

describe('BtServiceOrderReturnService', () => {
  let service: BtServiceOrderReturnService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtServiceOrderReturnService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
