import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtCreateOtherChargesComponent } from './bt-create-other-charges.component';

describe('BtCreateOtherChargesComponent', () => {
  let component: BtCreateOtherChargesComponent;
  let fixture: ComponentFixture<BtCreateOtherChargesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateOtherChargesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateOtherChargesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
