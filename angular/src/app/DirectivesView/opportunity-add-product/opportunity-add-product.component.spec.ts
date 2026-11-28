import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { OpportunityAddProductComponent } from './opportunity-add-product.component';

describe('OpportunityAddProductComponent', () => {
  let component: OpportunityAddProductComponent;
  let fixture: ComponentFixture<OpportunityAddProductComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ OpportunityAddProductComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OpportunityAddProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
