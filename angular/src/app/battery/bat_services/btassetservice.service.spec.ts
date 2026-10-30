import { TestBed } from '@angular/core/testing';

import { BTassetserviceService } from './btassetservice.service';

describe('BTassetserviceService', () => {
  let service: BTassetserviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BTassetserviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
