import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatWarehouseComponent } from './bat-warehouse.component';

describe('BatWarehouseComponent', () => {
  let component: BatWarehouseComponent;
  let fixture: ComponentFixture<BatWarehouseComponent>;

  beforeEach(async(() => {
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
