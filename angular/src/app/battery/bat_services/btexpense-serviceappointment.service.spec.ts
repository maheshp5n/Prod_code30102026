import { TestBed } from '@angular/core/testing';

import { BtexpenseServiceappointmentService } from './btexpense-serviceappointment.service';

describe('BtexpenseServiceappointmentService', () => {
  let service: BtexpenseServiceappointmentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BtexpenseServiceappointmentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
