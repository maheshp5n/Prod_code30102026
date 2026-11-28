import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { LegalentityviewComponent } from './legalentityview.component';

describe('LegalentityviewComponent', () => {
  let component: LegalentityviewComponent;
  let fixture: ComponentFixture<LegalentityviewComponent>;

  beforeEach(waitForAsync(() => {
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
