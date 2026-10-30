import { TestBed } from '@angular/core/testing';

import { BtotherChargesExpenseService } from './btother-charges-expense.service';

describe('BtotherChargesExpenseService', () => {
  let service: BtotherChargesExpenseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtotherChargesExpenseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
