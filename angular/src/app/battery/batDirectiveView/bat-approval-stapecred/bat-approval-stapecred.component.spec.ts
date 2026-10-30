import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatApprovalStapecredComponent } from './bat-approval-stapecred.component';

describe('BatApprovalStapecredComponent', () => {
  let component: BatApprovalStapecredComponent;
  let fixture: ComponentFixture<BatApprovalStapecredComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatApprovalStapecredComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatApprovalStapecredComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
