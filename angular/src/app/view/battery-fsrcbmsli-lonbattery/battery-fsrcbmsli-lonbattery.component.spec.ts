import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatteryFSRCBMSLiLonbatteryComponent } from './battery-fsrcbmsli-lonbattery.component';

describe('BatteryFSRCBMSLiLonbatteryComponent', () => {
  let component: BatteryFSRCBMSLiLonbatteryComponent;
  let fixture: ComponentFixture<BatteryFSRCBMSLiLonbatteryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatteryFSRCBMSLiLonbatteryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatteryFSRCBMSLiLonbatteryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
