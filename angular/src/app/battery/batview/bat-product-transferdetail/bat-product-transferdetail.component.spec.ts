import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatProductTransferdetailComponent } from './bat-product-transferdetail.component';

describe('BatProductTransferdetailComponent', () => {
  let component: BatProductTransferdetailComponent;
  let fixture: ComponentFixture<BatProductTransferdetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatProductTransferdetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatProductTransferdetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
