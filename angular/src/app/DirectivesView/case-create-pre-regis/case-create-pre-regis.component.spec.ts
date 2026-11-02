import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CaseCreatePreRegisComponent } from './case-create-pre-regis.component';

describe('CaseCreatePreRegisComponent', () => {
  let component: CaseCreatePreRegisComponent;
  let fixture: ComponentFixture<CaseCreatePreRegisComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CaseCreatePreRegisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CaseCreatePreRegisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
