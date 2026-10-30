import { TestBed } from '@angular/core/testing';

import { BtCaseserviceService } from './bt-caseservice.service';

describe('BtCaseserviceService', () => {
  let service: BtCaseserviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtCaseserviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
