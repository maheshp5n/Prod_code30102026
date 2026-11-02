import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { HelpguideComponent } from './helpguide.component';

describe('HelpguideComponent', () => {
  let component: HelpguideComponent;
  let fixture: ComponentFixture<HelpguideComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ HelpguideComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HelpguideComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
