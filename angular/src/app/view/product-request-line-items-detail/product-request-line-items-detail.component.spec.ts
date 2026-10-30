import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductRequestLineItemsDetailComponent } from './product-request-line-items-detail.component';

describe('ProductRequestLineItemsDetailComponent', () => {
  let component: ProductRequestLineItemsDetailComponent;
  let fixture: ComponentFixture<ProductRequestLineItemsDetailComponent>;

  beforeEach(async(() => {
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
