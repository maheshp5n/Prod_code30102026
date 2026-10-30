import { TestBed } from '@angular/core/testing';

import { BttourExpenseService } from './bttour-expense.service';

describe('BttourExpenseService', () => {
  let service: BttourExpenseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BttourExpenseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
