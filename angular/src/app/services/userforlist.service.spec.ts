import { TestBed, inject } from '@angular/core/testing';

import { UserforlistService } from './userforlist.service';

describe('UserforlistService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [UserforlistService]
    });
  });

  it('should be created', inject([UserforlistService], (service: UserforlistService) => {
    expect(service).toBeTruthy();
  }));
});
