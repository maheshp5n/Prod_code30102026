import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtProductTransferReceiveComponent } from './bt-product-transfer-receive.component';

describe('BtProductTransferReceiveComponent', () => {
  let component: BtProductTransferReceiveComponent;
  let fixture: ComponentFixture<BtProductTransferReceiveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtProductTransferReceiveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtProductTransferReceiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
