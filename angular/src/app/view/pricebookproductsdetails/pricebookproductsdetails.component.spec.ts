import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { PricebookproductsdetailsComponent } from './pricebookproductsdetails.component';

describe('PricebookproductsdetailsComponent', () => {
  let component: PricebookproductsdetailsComponent;
  let fixture: ComponentFixture<PricebookproductsdetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ PricebookproductsdetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PricebookproductsdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
