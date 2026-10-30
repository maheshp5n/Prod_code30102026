import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatProductReturnDetailRelatedComponent } from './bat-product-return-detail-related.component';

describe('BatProductReturnDetailRelatedComponent', () => {
  let component: BatProductReturnDetailRelatedComponent;
  let fixture: ComponentFixture<BatProductReturnDetailRelatedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatProductReturnDetailRelatedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatProductReturnDetailRelatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
