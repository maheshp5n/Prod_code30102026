import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CasePreRegisComponent } from './case-pre-regis.component';

describe('CasePreRegisComponent', () => {
  let component: CasePreRegisComponent;
  let fixture: ComponentFixture<CasePreRegisComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CasePreRegisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CasePreRegisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
