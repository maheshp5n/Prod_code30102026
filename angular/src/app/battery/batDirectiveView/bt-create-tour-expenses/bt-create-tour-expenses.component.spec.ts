import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtCreateTourExpensesComponent } from './bt-create-tour-expenses.component';

describe('BtCreateTourExpensesComponent', () => {
  let component: BtCreateTourExpensesComponent;
  let fixture: ComponentFixture<BtCreateTourExpensesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateTourExpensesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateTourExpensesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
