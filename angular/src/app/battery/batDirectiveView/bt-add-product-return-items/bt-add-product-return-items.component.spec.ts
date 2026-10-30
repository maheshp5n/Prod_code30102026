import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtAddProductReturnItemsComponent } from './bt-add-product-return-items.component';

describe('BtAddProductReturnItemsComponent', () => {
  let component: BtAddProductReturnItemsComponent;
  let fixture: ComponentFixture<BtAddProductReturnItemsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtAddProductReturnItemsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtAddProductReturnItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
