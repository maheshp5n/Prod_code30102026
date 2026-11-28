import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateboardingComponent } from './createboarding.component';

describe('CreateboardingComponent', () => {
  let component: CreateboardingComponent;
  let fixture: ComponentFixture<CreateboardingComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateboardingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateboardingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
