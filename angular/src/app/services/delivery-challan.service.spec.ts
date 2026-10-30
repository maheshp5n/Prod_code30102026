import { TestBed } from '@angular/core/testing';

import { deliverychallanService } from './delivery-challan.service';

describe('deliverychallanService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: deliverychallanService = TestBed.get(deliverychallanService);
    expect(service).toBeTruthy();
  });
});
