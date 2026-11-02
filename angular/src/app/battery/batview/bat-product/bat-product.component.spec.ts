import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatProductComponent } from './bat-product.component';

describe('BatProductComponent', () => {
  let component: BatProductComponent;
  let fixture: ComponentFixture<BatProductComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatProductComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
