import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateExpensesReportComponent } from './create-expenses-report.component';

describe('CreateExpensesReportComponent', () => {
  let component: CreateExpensesReportComponent;
  let fixture: ComponentFixture<CreateExpensesReportComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateExpensesReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateExpensesReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
