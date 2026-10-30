import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtExpenseCreateAccountOnlyComponent } from './bt-expense-create-account-only.component';

describe('BtExpenseCreateAccountOnlyComponent', () => {
  let component: BtExpenseCreateAccountOnlyComponent;
  let fixture: ComponentFixture<BtExpenseCreateAccountOnlyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtExpenseCreateAccountOnlyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtExpenseCreateAccountOnlyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
