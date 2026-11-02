import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtCreateServiceOrderReturnComponent } from './bt-create-service-order-return.component';

describe('BtCreateServiceOrderReturnComponent', () => {
  let component: BtCreateServiceOrderReturnComponent;
  let fixture: ComponentFixture<BtCreateServiceOrderReturnComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateServiceOrderReturnComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateServiceOrderReturnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
