import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatWarehouseComponent } from './bat-warehouse.component';

describe('BatWarehouseComponent', () => {
  let component: BatWarehouseComponent;
  let fixture: ComponentFixture<BatWarehouseComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatWarehouseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatWarehouseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
