import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatCaseDetailComponent } from './bat-case-detail.component';

describe('BatCaseDetailComponent', () => {
  let component: BatCaseDetailComponent;
  let fixture: ComponentFixture<BatCaseDetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatCaseDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatCaseDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
