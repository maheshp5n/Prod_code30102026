import { TestBed } from '@angular/core/testing';

import { BtuserroleService } from './btuserrole.service';

describe('BtuserroleService', () => {
  let service: BtuserroleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtuserroleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
