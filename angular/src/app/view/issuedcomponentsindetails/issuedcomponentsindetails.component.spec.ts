import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { IssuedcomponentsindetailsComponent } from './issuedcomponentsindetails.component';

describe('IssuedcomponentsindetailsComponent', () => {
  let component: IssuedcomponentsindetailsComponent;
  let fixture: ComponentFixture<IssuedcomponentsindetailsComponent>;

  beforeEach(waitForAsync(() => {
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
