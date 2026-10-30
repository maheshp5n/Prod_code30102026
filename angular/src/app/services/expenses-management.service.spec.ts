import { TestBed } from '@angular/core/testing';

import { ExpenseManagementService } from './expenses-management.service';

describe('ExpenseManagementService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ExpenseManagementService = TestBed.get(ExpenseManagementService);
    expect(service).toBeTruthy();
  });
});
