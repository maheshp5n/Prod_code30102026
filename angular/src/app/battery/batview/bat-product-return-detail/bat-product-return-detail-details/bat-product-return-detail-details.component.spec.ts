import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatProductReturnDetailDetailsComponent } from './bat-product-return-detail-details.component';

describe('BatProductReturnDetailDetailsComponent', () => {
  let component: BatProductReturnDetailDetailsComponent;
  let fixture: ComponentFixture<BatProductReturnDetailDetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatProductReturnDetailDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatProductReturnDetailDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
