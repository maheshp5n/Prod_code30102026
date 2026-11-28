import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateApprovalComponent } from './create-approvalWorkflow.component';

describe('CreateApprovalComponent', () => {
  let component: CreateApprovalComponent;
  let fixture: ComponentFixture<CreateApprovalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [CreateApprovalComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateApprovalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
