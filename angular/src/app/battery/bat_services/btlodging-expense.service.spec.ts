import { TestBed } from '@angular/core/testing';

import { BtlodgingExpenseService } from './btlodging-expense.service';

describe('BtlodgingExpenseService', () => {
  let service: BtlodgingExpenseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtlodgingExpenseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
