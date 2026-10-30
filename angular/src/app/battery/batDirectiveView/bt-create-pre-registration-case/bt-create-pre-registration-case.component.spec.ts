import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCreatePreRegistrationCaseComponent } from './bt-create-pre-registration-case.component';

describe('BtCreatePreRegistrationCaseComponent', () => {
  let component: BtCreatePreRegistrationCaseComponent;
  let fixture: ComponentFixture<BtCreatePreRegistrationCaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreatePreRegistrationCaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreatePreRegistrationCaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
