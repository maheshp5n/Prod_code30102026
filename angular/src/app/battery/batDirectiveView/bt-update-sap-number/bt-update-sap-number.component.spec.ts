import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtUpdateSapNumberComponent } from './bt-update-sap-number.component';

describe('BtUpdateSapNumberComponent', () => {
  let component: BtUpdateSapNumberComponent;
  let fixture: ComponentFixture<BtUpdateSapNumberComponent>;

  beforeEach(async(() => {
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
