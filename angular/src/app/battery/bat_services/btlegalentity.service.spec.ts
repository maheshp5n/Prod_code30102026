import { TestBed } from '@angular/core/testing';

import { BtlegalentityService } from './btlegalentity.service';

describe('BtlegalentityService', () => {
  let service: BtlegalentityService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtlegalentityService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
