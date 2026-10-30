import { TestBed } from '@angular/core/testing';

import { BtCommercialService } from './bt-commercial.service';

describe('BtCommercialService', () => {
  let service: BtCommercialService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtCommercialService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
