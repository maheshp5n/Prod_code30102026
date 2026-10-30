import { TestBed ,inject} from '@angular/core/testing';

import { BtboardingExpenseService } from './btboarding-expense.service';

describe('BtboardingExpenseService', () => {
  let service: BtboardingExpenseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers:[BtboardingExpenseService]
    });
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
