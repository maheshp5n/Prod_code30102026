import { TestBed } from '@angular/core/testing';

import { BtMyInventoryService } from './bt-my-inventory.service';

describe('BtMyInventoryService', () => {
  let service: BtMyInventoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtMyInventoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
