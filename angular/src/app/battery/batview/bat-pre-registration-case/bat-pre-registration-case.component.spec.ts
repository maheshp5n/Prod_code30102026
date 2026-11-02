import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatPreRegistrationCaseComponent } from './bat-pre-registration-case.component';

describe('BatPreRegistrationCaseComponent', () => {
  let component: BatPreRegistrationCaseComponent;
  let fixture: ComponentFixture<BatPreRegistrationCaseComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatPreRegistrationCaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatPreRegistrationCaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
