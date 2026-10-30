import { TestBed } from '@angular/core/testing';

import { BtserviceresourseService } from './btserviceresourse.service';

describe('BtserviceresourseService', () => {
  let service: BtserviceresourseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtserviceresourseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
