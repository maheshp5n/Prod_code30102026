import { TestBed } from '@angular/core/testing';

import { BtServiceappointmentService } from './bt-serviceappointment.service';

describe('BtServiceappointmentService', () => {
  let service: BtServiceappointmentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtServiceappointmentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
