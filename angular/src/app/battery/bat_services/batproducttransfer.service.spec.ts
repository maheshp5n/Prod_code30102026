import { TestBed } from '@angular/core/testing';

import { BatproducttransferService } from './batproducttransfer.service';

describe('BatproducttransferService', () => {
  let service: BatproducttransferService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BatproducttransferService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
