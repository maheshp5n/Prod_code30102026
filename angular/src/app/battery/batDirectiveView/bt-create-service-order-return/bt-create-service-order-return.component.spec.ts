import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCreateServiceOrderReturnComponent } from './bt-create-service-order-return.component';

describe('BtCreateServiceOrderReturnComponent', () => {
  let component: BtCreateServiceOrderReturnComponent;
  let fixture: ComponentFixture<BtCreateServiceOrderReturnComponent>;

  beforeEach(async(() => {
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
