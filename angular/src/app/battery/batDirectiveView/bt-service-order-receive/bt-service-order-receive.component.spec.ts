import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtServiceOrderReceiveComponent } from './bt-service-order-receive.component';

describe('BtServiceOrderReceiveComponent', () => {
  let component: BtServiceOrderReceiveComponent;
  let fixture: ComponentFixture<BtServiceOrderReceiveComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtServiceOrderReceiveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtServiceOrderReceiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
