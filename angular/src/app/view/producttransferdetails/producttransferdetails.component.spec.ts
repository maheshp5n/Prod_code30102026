import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProducttransferdetailsComponent } from './producttransferdetails.component';

describe('ProducttransferdetailsComponent', () => {
  let component: ProducttransferdetailsComponent;
  let fixture: ComponentFixture<ProducttransferdetailsComponent>;

  beforeEach(async(() => {
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
