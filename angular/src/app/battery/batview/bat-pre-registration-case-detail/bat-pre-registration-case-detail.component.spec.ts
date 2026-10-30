import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatPreRegistrationCaseDetailComponent } from './bat-pre-registration-case-detail.component';

describe('BatPreRegistrationCaseDetailComponent', () => {
  let component: BatPreRegistrationCaseDetailComponent;
  let fixture: ComponentFixture<BatPreRegistrationCaseDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatPreRegistrationCaseDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatPreRegistrationCaseDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
