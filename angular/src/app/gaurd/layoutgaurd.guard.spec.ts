import { TestBed, inject, waitForAsync } from '@angular/core/testing';

import { LayoutgaurdGuard } from './layoutgaurd.guard';

describe('LayoutgaurdGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LayoutgaurdGuard]
    });
  });

  it('should ...', inject([LayoutgaurdGuard], (guard: LayoutgaurdGuard) => {
    expect(guard).toBeTruthy();
  }));
});
