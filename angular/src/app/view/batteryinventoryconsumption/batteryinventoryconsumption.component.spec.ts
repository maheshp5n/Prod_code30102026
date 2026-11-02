import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatteryinventoryconsumptionComponent } from './batteryinventoryconsumption.component';

describe('BatteryinventoryconsumptionComponent', () => {
  let component: BatteryinventoryconsumptionComponent;
  let fixture: ComponentFixture<BatteryinventoryconsumptionComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatteryinventoryconsumptionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatteryinventoryconsumptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
