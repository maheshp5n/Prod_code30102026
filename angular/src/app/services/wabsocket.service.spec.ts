import { TestBed, inject } from '@angular/core/testing';

import { WabsocketService } from './wabsocket.service';

describe('WabsocketService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [WabsocketService]
    });
  });

  it('should be created', inject([WabsocketService], (service: WabsocketService) => {
    expect(service).toBeTruthy();
  }));
});
