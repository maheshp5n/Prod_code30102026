import { TestBed } from '@angular/core/testing';

import { BatuserregistrationService } from './batuserregistration.service';

describe('BatuserregistrationService', () => {
  let service: BatuserregistrationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BatuserregistrationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
