import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpensesmanagementComponent } from './expensesmanagement.component';

describe('ExpensesmanagementComponent', () => {
  let component: ExpensesmanagementComponent;
  let fixture: ComponentFixture<ExpensesmanagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ExpensesmanagementComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ExpensesmanagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
