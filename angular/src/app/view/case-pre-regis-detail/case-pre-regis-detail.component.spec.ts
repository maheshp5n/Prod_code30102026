import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CasePreRegisDetailComponent } from './case-pre-regis-detail.component';

describe('CasePreRegisDetailComponent', () => {
  let component: CasePreRegisDetailComponent;
  let fixture: ComponentFixture<CasePreRegisDetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CasePreRegisDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CasePreRegisDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
