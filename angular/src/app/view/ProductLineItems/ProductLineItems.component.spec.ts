import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ProductLineItemsComponent } from './productLineItems.component';

describe('ProductLineItemsComponent', () => {
  let component: ProductLineItemsComponent;
  let fixture: ComponentFixture<ProductLineItemsComponent>;

  beforeEach(async(() => {
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
