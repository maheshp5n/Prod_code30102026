import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CaseDetailsComponent } from './case-details.component';

describe('CaseDetailsComponent', () => {
  let component: CaseDetailsComponent;
  let fixture: ComponentFixture<CaseDetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CaseDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CaseDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
