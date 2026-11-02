import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateAccountOnlyComponent } from './create-account-only.component';

describe('CreateAccountOnlyComponent', () => {
  let component: CreateAccountOnlyComponent;
  let fixture: ComponentFixture<CreateAccountOnlyComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateAccountOnlyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateAccountOnlyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
