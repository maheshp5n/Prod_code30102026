import { TestBed } from '@angular/core/testing';

import { BtFsrService } from './bt-fsr.service';

describe('BtFsrService', () => {
  let service: BtFsrService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtFsrService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
