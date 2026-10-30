import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LegalentityviewComponent } from './legalentityview.component';

describe('LegalentityviewComponent', () => {
  let component: LegalentityviewComponent;
  let fixture: ComponentFixture<LegalentityviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LegalentityviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LegalentityviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
