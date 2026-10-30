import { TestBed } from '@angular/core/testing';

import { BtRmaService } from './bt-rma.service';

describe('BtRmaService', () => {
  let service: BtRmaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtRmaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
