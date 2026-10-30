import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNewexpensesmanagementComponent } from './create-new-expensesmanagement.component';

describe('CreateNewexpensesmanagementComponent', () => {
  let component: CreateNewexpensesmanagementComponent;
  let fixture: ComponentFixture<CreateNewexpensesmanagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewexpensesmanagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewexpensesmanagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
