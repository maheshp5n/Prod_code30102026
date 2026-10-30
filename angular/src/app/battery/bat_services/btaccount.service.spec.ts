import { TestBed } from '@angular/core/testing';

import { BtaccountService } from './btaccount.service';

describe('BtaccountService', () => {
  let service: BtaccountService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtaccountService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
