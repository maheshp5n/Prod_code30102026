import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatteryFSRCBMSLiLonbatterydetailsComponent } from './battery-fsrcbmsli-lonbatterydetails.component';

describe('BatteryFSRCBMSLiLonbatterydetailsComponent', () => {
  let component: BatteryFSRCBMSLiLonbatterydetailsComponent;
  let fixture: ComponentFixture<BatteryFSRCBMSLiLonbatterydetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatteryFSRCBMSLiLonbatterydetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatteryFSRCBMSLiLonbatterydetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
