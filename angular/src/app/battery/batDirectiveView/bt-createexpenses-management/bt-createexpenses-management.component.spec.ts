import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtCreateexpensesManagementComponent } from './bt-createexpenses-management.component';

describe('BtCreateexpensesManagementComponent', () => {
  let component: BtCreateexpensesManagementComponent;
  let fixture: ComponentFixture<BtCreateexpensesManagementComponent>;

  beforeEach(waitForAsync(() => {
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
