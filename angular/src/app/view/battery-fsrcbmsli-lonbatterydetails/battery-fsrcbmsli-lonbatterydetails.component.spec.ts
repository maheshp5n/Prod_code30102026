import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatteryFSRCBMSLiLonbatterydetailsComponent } from './battery-fsrcbmsli-lonbatterydetails.component';

describe('BatteryFSRCBMSLiLonbatterydetailsComponent', () => {
  let component: BatteryFSRCBMSLiLonbatterydetailsComponent;
  let fixture: ComponentFixture<BatteryFSRCBMSLiLonbatterydetailsComponent>;

  beforeEach(async(() => {
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
