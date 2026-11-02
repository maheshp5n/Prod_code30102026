import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatUserregistrationdetailsComponent } from './bat-userregistrationdetails.component';

describe('BatUserregistrationdetailsComponent', () => {
  let component: BatUserregistrationdetailsComponent;
  let fixture: ComponentFixture<BatUserregistrationdetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatUserregistrationdetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatUserregistrationdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
