import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateReportsForExpenseComponent } from './create-reports-for-expense.component';

describe('CreateReportsForExpenseComponent', () => {
  let component: CreateReportsForExpenseComponent;
  let fixture: ComponentFixture<CreateReportsForExpenseComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateReportsForExpenseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateReportsForExpenseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
