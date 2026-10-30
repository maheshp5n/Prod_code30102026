import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatProductReturnDetailComponent } from './bat-product-return-detail.component';

describe('BatProductReturnDetailComponent', () => {
  let component: BatProductReturnDetailComponent;
  let fixture: ComponentFixture<BatProductReturnDetailComponent>;

  beforeEach(async(() => {
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
