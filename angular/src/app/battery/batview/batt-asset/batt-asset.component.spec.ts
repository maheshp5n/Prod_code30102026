import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BattAssetComponent } from './batt-asset.component';

describe('BattAssetComponent', () => {
  let component: BattAssetComponent;
  let fixture: ComponentFixture<BattAssetComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BattAssetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BattAssetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
