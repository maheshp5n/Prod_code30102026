import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtProducttransferComponent } from './bt-producttransfer.component';

describe('BtProducttransferComponent', () => {
  let component: BtProducttransferComponent;
  let fixture: ComponentFixture<BtProducttransferComponent>;

  beforeEach(async(() => {
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
