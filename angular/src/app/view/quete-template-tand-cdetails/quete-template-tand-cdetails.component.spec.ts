import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QueteTemplateTandCDetailsComponent } from './quete-template-tand-cdetails.component';

describe('QueteTemplateTandCDetailsComponent', () => {
  let component: QueteTemplateTandCDetailsComponent;
  let fixture: ComponentFixture<QueteTemplateTandCDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QueteTemplateTandCDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QueteTemplateTandCDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
