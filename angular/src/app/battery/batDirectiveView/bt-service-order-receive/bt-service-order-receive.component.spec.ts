import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtServiceOrderReceiveComponent } from './bt-service-order-receive.component';

describe('BtServiceOrderReceiveComponent', () => {
  let component: BtServiceOrderReceiveComponent;
  let fixture: ComponentFixture<BtServiceOrderReceiveComponent>;

  beforeEach(async(() => {
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
