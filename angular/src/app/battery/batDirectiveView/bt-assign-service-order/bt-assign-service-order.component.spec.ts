import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtAssignServiceOrderComponent } from './bt-assign-service-order.component';

describe('BtAssignServiceOrderComponent', () => {
  let component: BtAssignServiceOrderComponent;
  let fixture: ComponentFixture<BtAssignServiceOrderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtAssignServiceOrderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtAssignServiceOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
