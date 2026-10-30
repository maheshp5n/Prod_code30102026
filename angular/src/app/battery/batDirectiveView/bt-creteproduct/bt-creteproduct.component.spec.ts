import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCreteproductComponent } from './bt-creteproduct.component';

describe('BtCreteproductComponent', () => {
  let component: BtCreteproductComponent;
  let fixture: ComponentFixture<BtCreteproductComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreteproductComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreteproductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
