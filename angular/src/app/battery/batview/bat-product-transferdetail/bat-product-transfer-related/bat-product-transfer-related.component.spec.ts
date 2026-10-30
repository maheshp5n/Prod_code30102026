import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatProductTransferRelatedComponent } from './bat-product-transfer-related.component';

describe('BatProductTransferRelatedComponent', () => {
  let component: BatProductTransferRelatedComponent;
  let fixture: ComponentFixture<BatProductTransferRelatedComponent>;

  beforeEach(async(() => {
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
