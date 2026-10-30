import { TestBed } from '@angular/core/testing';

import { BtPreRegistrationCaseService } from './bt-pre-registration-case.service';

describe('BtPreRegistrationCaseService', () => {
  let service: BtPreRegistrationCaseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtPreRegistrationCaseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
