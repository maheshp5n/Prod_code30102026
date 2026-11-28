import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatProductTransferDetailsComponent } from './bat-product-transfer-details.component';

describe('BatProductTransferDetailsComponent', () => {
  let component: BatProductTransferDetailsComponent;
  let fixture: ComponentFixture<BatProductTransferDetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatProductTransferDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatProductTransferDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
