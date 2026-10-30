import { TestBed } from '@angular/core/testing';

import { BtMslrequestService } from './bt-mslrequest.service';

describe('BtMslrequestService', () => {
  let service: BtMslrequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtMslrequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
