import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { QuotePdfTemplateComponent } from './quote-pdf-template.component';

describe('QuotePdfTemplateComponent', () => {
  let component: QuotePdfTemplateComponent;
  let fixture: ComponentFixture<QuotePdfTemplateComponent>;

  beforeEach(waitForAsync(() => {
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
