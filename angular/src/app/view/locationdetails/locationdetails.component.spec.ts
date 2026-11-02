import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { LocationdetailsComponent } from './locationdetails.component';

describe('LocationdetailsComponent', () => {
  let component: LocationdetailsComponent;
  let fixture: ComponentFixture<LocationdetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ LocationdetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LocationdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
