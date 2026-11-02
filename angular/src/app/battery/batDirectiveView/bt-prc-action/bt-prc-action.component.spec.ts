import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtPrcActionComponent } from './bt-prc-action.component';

describe('BtPrcActionComponent', () => {
  let component: BtPrcActionComponent;
  let fixture: ComponentFixture<BtPrcActionComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtPrcActionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtPrcActionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
