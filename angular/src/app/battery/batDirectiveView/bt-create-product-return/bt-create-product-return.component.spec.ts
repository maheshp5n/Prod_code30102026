import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtCreateProductReturnComponent } from './bt-create-product-return.component';

describe('BtCreateProductReturnComponent', () => {
  let component: BtCreateProductReturnComponent;
  let fixture: ComponentFixture<BtCreateProductReturnComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateProductReturnComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateProductReturnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
