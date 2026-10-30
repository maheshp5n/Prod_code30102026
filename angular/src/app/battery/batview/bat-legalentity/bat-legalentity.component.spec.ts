import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatLegalentityComponent } from './bat-legalentity.component';

describe('BatLegalentityComponent', () => {
  let component: BatLegalentityComponent;
  let fixture: ComponentFixture<BatLegalentityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatLegalentityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatLegalentityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
