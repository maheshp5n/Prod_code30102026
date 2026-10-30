import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCreateLocalConveyanceExpenseComponent } from './bt-create-local-conveyance-expense.component';

describe('BtCreateLocalConveyanceExpenseComponent', () => {
  let component: BtCreateLocalConveyanceExpenseComponent;
  let fixture: ComponentFixture<BtCreateLocalConveyanceExpenseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateLocalConveyanceExpenseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateLocalConveyanceExpenseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
