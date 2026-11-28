import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatExpensesmanagementComponent } from './bat-expensesmanagement.component';

describe('BatExpensesmanagementComponent', () => {
  let component: BatExpensesmanagementComponent;
  let fixture: ComponentFixture<BatExpensesmanagementComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatExpensesmanagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatExpensesmanagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
