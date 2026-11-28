import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatProductReturnComponent } from './bat-product-return.component';

describe('BatProductReturnComponent', () => {
  let component: BatProductReturnComponent;
  let fixture: ComponentFixture<BatProductReturnComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatProductReturnComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatProductReturnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
