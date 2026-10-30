import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { CreateProductEditToPriceBookComponent } from './create-product-edit-to-price-book.component';

describe('CreateProductEditToPriceBookComponent', () => {
  let component: CreateProductEditToPriceBookComponent;
  let fixture: ComponentFixture<CreateProductEditToPriceBookComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [CreateProductEditToPriceBookComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateProductEditToPriceBookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
