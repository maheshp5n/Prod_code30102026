import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { IssuedcomponentsComponent } from './issuedcomponents.component';

describe('IssuedcomponentsComponent', () => {
  let component: IssuedcomponentsComponent;
  let fixture: ComponentFixture<IssuedcomponentsComponent>;

  beforeEach(waitForAsync(() => {
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
