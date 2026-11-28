import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtUpdateSapNumberComponent } from './bt-update-sap-number.component';

describe('BtUpdateSapNumberComponent', () => {
  let component: BtUpdateSapNumberComponent;
  let fixture: ComponentFixture<BtUpdateSapNumberComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtUpdateSapNumberComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtUpdateSapNumberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
