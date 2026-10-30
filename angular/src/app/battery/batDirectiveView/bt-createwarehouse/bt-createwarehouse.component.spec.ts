import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCreatewarehouseComponent } from './bt-createwarehouse.component';

describe('BtCreatewarehouseComponent', () => {
  let component: BtCreatewarehouseComponent;
  let fixture: ComponentFixture<BtCreatewarehouseComponent>;

  beforeEach(async(() => {
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
