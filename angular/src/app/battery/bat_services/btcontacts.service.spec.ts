import { TestBed } from '@angular/core/testing';

import { BtcontactsService } from './btcontacts.service';

describe('BtcontactsService', () => {
  let service: BtcontactsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtcontactsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
