import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IssuedcomponentsindetailsComponent } from './issuedcomponentsindetails.component';

describe('IssuedcomponentsindetailsComponent', () => {
  let component: IssuedcomponentsindetailsComponent;
  let fixture: ComponentFixture<IssuedcomponentsindetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IssuedcomponentsindetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IssuedcomponentsindetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
