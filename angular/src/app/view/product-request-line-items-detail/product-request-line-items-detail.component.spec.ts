import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ProductRequestLineItemsDetailComponent } from './product-request-line-items-detail.component';

describe('ProductRequestLineItemsDetailComponent', () => {
  let component: ProductRequestLineItemsDetailComponent;
  let fixture: ComponentFixture<ProductRequestLineItemsDetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductRequestLineItemsDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductRequestLineItemsDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
