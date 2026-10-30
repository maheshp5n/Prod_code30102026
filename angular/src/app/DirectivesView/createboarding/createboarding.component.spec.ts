import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateboardingComponent } from './createboarding.component';

describe('CreateboardingComponent', () => {
  let component: CreateboardingComponent;
  let fixture: ComponentFixture<CreateboardingComponent>;

  beforeEach(async(() => {
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
