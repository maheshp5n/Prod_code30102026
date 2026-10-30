import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatProductRequestItemsDetailsComponent } from './bat-product-request-items-details.component';

describe('BatProductRequestItemsDetailsComponent', () => {
  let component: BatProductRequestItemsDetailsComponent;
  let fixture: ComponentFixture<BatProductRequestItemsDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatProductRequestItemsDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatProductRequestItemsDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
