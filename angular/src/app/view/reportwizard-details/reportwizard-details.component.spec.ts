import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportwizardDetailsComponent } from './reportwizard-details.component';

describe('ReportwizardDetailsComponent', () => {
  let component: ReportwizardDetailsComponent;
  let fixture: ComponentFixture<ReportwizardDetailsComponent>;

  beforeEach(async(() => {
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
