import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatUserregistrationdetailsComponent } from './bat-userregistrationdetails.component';

describe('BatUserregistrationdetailsComponent', () => {
  let component: BatUserregistrationdetailsComponent;
  let fixture: ComponentFixture<BatUserregistrationdetailsComponent>;

  beforeEach(async(() => {
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
