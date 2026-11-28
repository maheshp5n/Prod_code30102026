import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { QuoteAddProductComponent } from './quote-add-product.component';

describe('QuoteAddProductComponent', () => {
  let component: QuoteAddProductComponent;
  let fixture: ComponentFixture<QuoteAddProductComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [QuoteAddProductComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuoteAddProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
