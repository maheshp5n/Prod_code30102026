import { TestBed } from '@angular/core/testing';

import { BtlocalConveyanceExpenseService } from './btlocal-conveyance-expense.service';

describe('BtlocalConveyanceExpenseService', () => {
  let service: BtlocalConveyanceExpenseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtlocalConveyanceExpenseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
