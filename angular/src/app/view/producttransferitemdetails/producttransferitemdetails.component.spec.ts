import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ProducttransferitemdetailsComponent } from './producttransferitemdetails.component';

describe('ProducttransferitemdetailsComponent', () => {
  let component: ProducttransferitemdetailsComponent;
  let fixture: ComponentFixture<ProducttransferitemdetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ProducttransferitemdetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProducttransferitemdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
