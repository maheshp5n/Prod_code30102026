import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatWarehouseDetailsComponent } from './bat-warehouse-details.component';

describe('BatWarehouseDetailsComponent', () => {
  let component: BatWarehouseDetailsComponent;
  let fixture: ComponentFixture<BatWarehouseDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatWarehouseDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatWarehouseDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
