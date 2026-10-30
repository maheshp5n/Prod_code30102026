import { TestBed, inject } from '@angular/core/testing';

import { BoardingLoadingService } from './boarding-loading.service';

describe('BoardingLoadingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BoardingLoadingService]
    });
  });

  it('should be created', inject([BoardingLoadingService], (service: BoardingLoadingService) => {
    expect(service).toBeTruthy();
  }));
});
