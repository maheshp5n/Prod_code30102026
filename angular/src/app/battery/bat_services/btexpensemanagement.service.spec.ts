import { TestBed } from '@angular/core/testing';

import { BtexpensemanagementService } from './btexpensemanagement.service';

describe('BtexpensemanagementService', () => {
  let service: BtexpensemanagementService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtexpensemanagementService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
