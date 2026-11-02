import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ProductTransferLineItemDetailsComponent } from './product-transfer-line-item-details.component';

describe('ProductTransferLineItemDetailsComponent', () => {
  let component: ProductTransferLineItemDetailsComponent;
  let fixture: ComponentFixture<ProductTransferLineItemDetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductTransferLineItemDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductTransferLineItemDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
