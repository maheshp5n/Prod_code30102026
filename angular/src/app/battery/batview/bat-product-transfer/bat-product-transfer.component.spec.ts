import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatProductTransferComponent } from './bat-product-transfer.component';

describe('BatProductTransferComponent', () => {
  let component: BatProductTransferComponent;
  let fixture: ComponentFixture<BatProductTransferComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatProductTransferComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatProductTransferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
