import { TestBed } from '@angular/core/testing';

import { BthelpguideService } from './bthelpguide.service';

describe('BthelpguideService', () => {
  let service: BthelpguideService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BthelpguideService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
