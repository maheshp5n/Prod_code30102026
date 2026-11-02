import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ExpenseConfirmDeleteComponent } from './expense-confirm-delete.component';

describe('ExpenseConfirmDeleteComponent', () => {
  let component: ExpenseConfirmDeleteComponent;
  let fixture: ComponentFixture<ExpenseConfirmDeleteComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ExpenseConfirmDeleteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExpenseConfirmDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
