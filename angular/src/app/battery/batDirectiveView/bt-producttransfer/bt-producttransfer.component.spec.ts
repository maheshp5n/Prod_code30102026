import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtProducttransferComponent } from './bt-producttransfer.component';

describe('BtProducttransferComponent', () => {
  let component: BtProducttransferComponent;
  let fixture: ComponentFixture<BtProducttransferComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtProducttransferComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtProducttransferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
