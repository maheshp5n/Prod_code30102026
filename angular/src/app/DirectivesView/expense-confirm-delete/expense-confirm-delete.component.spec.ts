import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpenseConfirmDeleteComponent } from './expense-confirm-delete.component';

describe('ExpenseConfirmDeleteComponent', () => {
  let component: ExpenseConfirmDeleteComponent;
  let fixture: ComponentFixture<ExpenseConfirmDeleteComponent>;

  beforeEach(async(() => {
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
