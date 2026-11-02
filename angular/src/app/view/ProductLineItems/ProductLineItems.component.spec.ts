import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { ProductLineItemsComponent } from './productLineItems.component';

describe('ProductLineItemsComponent', () => {
  let component: ProductLineItemsComponent;
  let fixture: ComponentFixture<ProductLineItemsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductLineItemsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductLineItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
