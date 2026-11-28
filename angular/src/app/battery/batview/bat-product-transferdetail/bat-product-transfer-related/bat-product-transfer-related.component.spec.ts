import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatProductTransferRelatedComponent } from './bat-product-transfer-related.component';

describe('BatProductTransferRelatedComponent', () => {
  let component: BatProductTransferRelatedComponent;
  let fixture: ComponentFixture<BatProductTransferRelatedComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatProductTransferRelatedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatProductTransferRelatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
