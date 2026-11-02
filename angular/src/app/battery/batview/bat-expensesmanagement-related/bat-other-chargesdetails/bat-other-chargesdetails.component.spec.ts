import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatOtherChargesdetailsComponent } from './bat-other-chargesdetails.component';

describe('BatOtherChargesdetailsComponent', () => {
  let component: BatOtherChargesdetailsComponent;
  let fixture: ComponentFixture<BatOtherChargesdetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatOtherChargesdetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatOtherChargesdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
