import { TestBed } from '@angular/core/testing';

import { BtReportService } from './bt-report.service';

describe('BtReportService', () => {
  let service: BtReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtReportService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
