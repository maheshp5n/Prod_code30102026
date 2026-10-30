import { TestBed } from '@angular/core/testing';

import { BtwarehouseService } from './btwarehouse.service';

describe('BtwarehouseService', () => {
  let service: BtwarehouseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtwarehouseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
