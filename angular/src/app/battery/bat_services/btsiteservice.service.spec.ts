import { TestBed } from '@angular/core/testing';

import { BtsiteserviceService } from './btsiteservice.service';

describe('BtsiteserviceService', () => {
  let service: BtsiteserviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtsiteserviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
