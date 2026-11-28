import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { ApprovaldetailsComponent } from './approvaldetails.component';

describe('ApprovaldetailsComponent', () => {
  let component: ApprovaldetailsComponent;
  let fixture: ComponentFixture<ApprovaldetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ApprovaldetailsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApprovaldetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
