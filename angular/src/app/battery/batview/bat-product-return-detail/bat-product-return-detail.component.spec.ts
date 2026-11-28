import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatProductReturnDetailComponent } from './bat-product-return-detail.component';

describe('BatProductReturnDetailComponent', () => {
  let component: BatProductReturnDetailComponent;
  let fixture: ComponentFixture<BatProductReturnDetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatProductReturnDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatProductReturnDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
