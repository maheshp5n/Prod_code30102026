import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtRejectCaseComponent } from './bt-reject-case.component';

describe('BtRejectCaseComponent', () => {
  let component: BtRejectCaseComponent;
  let fixture: ComponentFixture<BtRejectCaseComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtRejectCaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtRejectCaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
