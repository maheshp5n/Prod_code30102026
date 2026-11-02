import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtCreatewarehouseComponent } from './bt-createwarehouse.component';

describe('BtCreatewarehouseComponent', () => {
  let component: BtCreatewarehouseComponent;
  let fixture: ComponentFixture<BtCreatewarehouseComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreatewarehouseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreatewarehouseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
