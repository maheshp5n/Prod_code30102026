import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtProductReturnReceiveComponent } from './bt-product-return-receive.component';

describe('BtProductReturnReceiveComponent', () => {
  let component: BtProductReturnReceiveComponent;
  let fixture: ComponentFixture<BtProductReturnReceiveComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtProductReturnReceiveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtProductReturnReceiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
