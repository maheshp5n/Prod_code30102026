import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatteryinventoryconsumptionComponent } from './batteryinventoryconsumption.component';

describe('BatteryinventoryconsumptionComponent', () => {
  let component: BatteryinventoryconsumptionComponent;
  let fixture: ComponentFixture<BatteryinventoryconsumptionComponent>;

  beforeEach(async(() => {
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
