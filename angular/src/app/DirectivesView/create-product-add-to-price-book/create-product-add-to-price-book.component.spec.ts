import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateProductAddToPriceBookComponent } from './create-product-add-to-price-book.component';

describe('CreateProductAddToPriceBookComponent', () => {
  let component: CreateProductAddToPriceBookComponent;
  let fixture: ComponentFixture<CreateProductAddToPriceBookComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateProductAddToPriceBookComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateProductAddToPriceBookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
