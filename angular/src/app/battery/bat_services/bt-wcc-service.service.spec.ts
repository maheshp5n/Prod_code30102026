import { TestBed } from '@angular/core/testing';

import { BtWccServiceService } from './bt-wcc-service.service';

describe('BtWccServiceService', () => {
  let service: BtWccServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtWccServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
