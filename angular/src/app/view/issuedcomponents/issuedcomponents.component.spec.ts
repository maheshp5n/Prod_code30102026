import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IssuedcomponentsComponent } from './issuedcomponents.component';

describe('IssuedcomponentsComponent', () => {
  let component: IssuedcomponentsComponent;
  let fixture: ComponentFixture<IssuedcomponentsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IssuedcomponentsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IssuedcomponentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
