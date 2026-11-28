import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ProducttransferdetailsComponent } from './producttransferdetails.component';

describe('ProducttransferdetailsComponent', () => {
  let component: ProducttransferdetailsComponent;
  let fixture: ComponentFixture<ProducttransferdetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ProducttransferdetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProducttransferdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
