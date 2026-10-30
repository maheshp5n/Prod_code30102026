import { TestBed } from '@angular/core/testing';

import { BtproductrequestService } from './btproductrequest.service';

describe('BtproductrequestService', () => {
  let service: BtproductrequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtproductrequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
