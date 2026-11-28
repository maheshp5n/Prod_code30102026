import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatteryinventoryconsumptionindetailsComponent } from './batteryinventoryconsumptionindetails.component';

describe('BatteryinventoryconsumptionindetailsComponent', () => {
  let component: BatteryinventoryconsumptionindetailsComponent;
  let fixture: ComponentFixture<BatteryinventoryconsumptionindetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatteryinventoryconsumptionindetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatteryinventoryconsumptionindetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
