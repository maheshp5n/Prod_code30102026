import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatLodgingdetailsComponent } from './bat-lodgingdetails.component';

describe('BatLodgingdetailsComponent', () => {
  let component: BatLodgingdetailsComponent;
  let fixture: ComponentFixture<BatLodgingdetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatLodgingdetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatLodgingdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
