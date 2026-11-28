import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtAddProductReturnItemsComponent } from './bt-add-product-return-items.component';

describe('BtAddProductReturnItemsComponent', () => {
  let component: BtAddProductReturnItemsComponent;
  let fixture: ComponentFixture<BtAddProductReturnItemsComponent>;

  beforeEach(waitForAsync(() => {
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
