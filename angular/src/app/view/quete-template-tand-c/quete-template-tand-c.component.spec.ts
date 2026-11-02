import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { QueteTemplateTandCComponent } from './quete-template-tand-c.component';

describe('QueteTemplateTandCComponent', () => {
  let component: QueteTemplateTandCComponent;
  let fixture: ComponentFixture<QueteTemplateTandCComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ QueteTemplateTandCComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QueteTemplateTandCComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
