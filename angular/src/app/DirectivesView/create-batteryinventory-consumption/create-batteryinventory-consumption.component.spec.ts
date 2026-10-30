import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateBatteryinventoryConsumptionComponent } from './create-batteryinventory-consumption.component';

describe('CreateBatteryinventoryConsumptionComponent', () => {
  let component: CreateBatteryinventoryConsumptionComponent;
  let fixture: ComponentFixture<CreateBatteryinventoryConsumptionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateBatteryinventoryConsumptionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateBatteryinventoryConsumptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
