import { TestBed } from '@angular/core/testing';

import { ServiceorderreturnService } from './serviceorderreturn.service';

describe('ServiceorderreturnService', () => {
  let service: ServiceorderreturnService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServiceorderreturnService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
