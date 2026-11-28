import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreatebatteryFSRCBMSLilonbatteryComponent } from './createbattery-fsrcbmslilonbattery.component';

describe('CreatebatteryFSRCBMSLilonbatteryComponent', () => {
  let component: CreatebatteryFSRCBMSLilonbatteryComponent;
  let fixture: ComponentFixture<CreatebatteryFSRCBMSLilonbatteryComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreatebatteryFSRCBMSLilonbatteryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatebatteryFSRCBMSLilonbatteryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
