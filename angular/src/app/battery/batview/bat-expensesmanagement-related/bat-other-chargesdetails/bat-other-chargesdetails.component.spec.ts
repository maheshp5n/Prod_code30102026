import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatOtherChargesdetailsComponent } from './bat-other-chargesdetails.component';

describe('BatOtherChargesdetailsComponent', () => {
  let component: BatOtherChargesdetailsComponent;
  let fixture: ComponentFixture<BatOtherChargesdetailsComponent>;

  beforeEach(async(() => {
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
