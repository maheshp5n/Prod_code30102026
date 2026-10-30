import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuotePdfTemplateComponent } from './quote-pdf-template.component';

describe('QuotePdfTemplateComponent', () => {
  let component: QuotePdfTemplateComponent;
  let fixture: ComponentFixture<QuotePdfTemplateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuotePdfTemplateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuotePdfTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
