import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCreateOtherChargesComponent } from './bt-create-other-charges.component';

describe('BtCreateOtherChargesComponent', () => {
  let component: BtCreateOtherChargesComponent;
  let fixture: ComponentFixture<BtCreateOtherChargesComponent>;

  beforeEach(async(() => {
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
