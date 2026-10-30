import { TestBed } from '@angular/core/testing';

import { BtcircleService } from './btcircle.service';

describe('BtcircleService', () => {
  let service: BtcircleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtcircleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
