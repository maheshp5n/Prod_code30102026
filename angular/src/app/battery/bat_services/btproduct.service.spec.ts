import { TestBed } from '@angular/core/testing';

import { BtproductService } from './btproduct.service';

describe('BtproductService', () => {
  let service: BtproductService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtproductService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
