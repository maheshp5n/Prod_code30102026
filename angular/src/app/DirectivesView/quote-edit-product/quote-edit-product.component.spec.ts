import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { QuoteEditProductComponent } from './quote-edit-product.component';


describe('QuoteEditProductComponent', () => {
  let component: QuoteEditProductComponent;
  let fixture: ComponentFixture<QuoteEditProductComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [QuoteEditProductComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuoteEditProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
