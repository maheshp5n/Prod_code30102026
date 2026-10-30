import { TestBed } from '@angular/core/testing';

import { BatJObCardQcService } from './bat-job-card-qc.service';

describe('BatJObCardQcService', () => {
  let service: BatJObCardQcService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BatJObCardQcService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
