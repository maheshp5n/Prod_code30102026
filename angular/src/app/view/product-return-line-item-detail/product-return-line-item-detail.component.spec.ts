import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ProductReturnLineItemDetailComponent } from './product-return-line-item-detail.component';

describe('ProductReturnLineItemDetailComponent', () => {
  let component: ProductReturnLineItemDetailComponent;
  let fixture: ComponentFixture<ProductReturnLineItemDetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductReturnLineItemDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductReturnLineItemDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
