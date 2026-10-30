import { TestBed } from '@angular/core/testing';

import { BtProductReturnService } from './bt-product-return.service';

describe('BtProductReturnService', () => {
  let service: BtProductReturnService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtProductReturnService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
