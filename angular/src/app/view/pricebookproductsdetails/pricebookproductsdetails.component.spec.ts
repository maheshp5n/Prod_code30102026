import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PricebookproductsdetailsComponent } from './pricebookproductsdetails.component';

describe('PricebookproductsdetailsComponent', () => {
  let component: PricebookproductsdetailsComponent;
  let fixture: ComponentFixture<PricebookproductsdetailsComponent>;

  beforeEach(async(() => {
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
