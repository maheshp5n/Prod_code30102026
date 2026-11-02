import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ReportwizardDetailsComponent } from './reportwizard-details.component';

describe('ReportwizardDetailsComponent', () => {
  let component: ReportwizardDetailsComponent;
  let fixture: ComponentFixture<ReportwizardDetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ReportwizardDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReportwizardDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
