import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatRejectCaseComponent } from './bat-reject-case.component';

describe('BatRejectCaseComponent', () => {
  let component: BatRejectCaseComponent;
  let fixture: ComponentFixture<BatRejectCaseComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatRejectCaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatRejectCaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
