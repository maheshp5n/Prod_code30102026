import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCreateexpensesManagementComponent } from './bt-createexpenses-management.component';

describe('BtCreateexpensesManagementComponent', () => {
  let component: BtCreateexpensesManagementComponent;
  let fixture: ComponentFixture<BtCreateexpensesManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateexpensesManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateexpensesManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
