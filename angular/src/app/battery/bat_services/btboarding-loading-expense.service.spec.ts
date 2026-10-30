import { TestBed } from '@angular/core/testing';

import { BtboardingLoadingExpenseService } from './btboarding-loading-expense.service';

describe('BtboardingLoadingExpenseService', () => {
  let service: BtboardingLoadingExpenseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtboardingLoadingExpenseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
