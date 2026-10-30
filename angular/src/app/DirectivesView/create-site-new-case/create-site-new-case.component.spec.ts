import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateSiteNewCaseComponent } from './create-site-new-case.component';

describe('CreateSiteNewCaseComponent', () => {
  let component: CreateSiteNewCaseComponent;
  let fixture: ComponentFixture<CreateSiteNewCaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateSiteNewCaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateSiteNewCaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
