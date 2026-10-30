import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CasetypeCaseresnPopupComponent } from './casetype-caseresn-popup.component';

describe('CasetypeCaseresnPopupComponent', () => {
  let component: CasetypeCaseresnPopupComponent;
  let fixture: ComponentFixture<CasetypeCaseresnPopupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CasetypeCaseresnPopupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CasetypeCaseresnPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
