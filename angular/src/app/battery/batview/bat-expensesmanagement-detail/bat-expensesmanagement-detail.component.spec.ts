import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatExpensesmanagementDetailComponent } from './bat-expensesmanagement-detail.component';

describe('BatExpensesmanagementDetailComponent', () => {
  let component: BatExpensesmanagementDetailComponent;
  let fixture: ComponentFixture<BatExpensesmanagementDetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatExpensesmanagementDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatExpensesmanagementDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
