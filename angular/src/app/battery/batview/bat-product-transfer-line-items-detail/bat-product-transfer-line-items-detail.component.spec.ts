import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatProductTransferLineItemsDetailComponent } from './bat-product-transfer-line-items-detail.component';

describe('BatProductTransferLineItemsDetailComponent', () => {
  let component: BatProductTransferLineItemsDetailComponent;
  let fixture: ComponentFixture<BatProductTransferLineItemsDetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatProductTransferLineItemsDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatProductTransferLineItemsDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
