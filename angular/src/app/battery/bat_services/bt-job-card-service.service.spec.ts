import { TestBed } from '@angular/core/testing';

import { BtJobCardServiceService } from './bt-job-card-service.service';

describe('BtJobCardServiceService', () => {
  let service: BtJobCardServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtJobCardServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
