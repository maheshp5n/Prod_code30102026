import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ProducttransferdetailsdetailComponent } from './producttransferdetailsdetail.component';

describe('ProducttransferdetailsdetailComponent', () => {
  let component: ProducttransferdetailsdetailComponent;
  let fixture: ComponentFixture<ProducttransferdetailsdetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ProducttransferdetailsdetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProducttransferdetailsdetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
