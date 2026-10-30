import { TestBed } from '@angular/core/testing';

import { ProductLineItemsService } from './product-line-items.service';

describe('ProductLineItemsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ProductLineItemsService = TestBed.get(ProductLineItemsService);
    expect(service).toBeTruthy();
  });
});
