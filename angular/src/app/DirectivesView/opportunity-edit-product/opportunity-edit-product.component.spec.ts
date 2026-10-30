import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OpportunityEditProductComponent } from './opportunity-edit-product.component';

describe('OpportunityEditProductComponent', () => {
  let component: OpportunityEditProductComponent;
  let fixture: ComponentFixture<OpportunityEditProductComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [OpportunityEditProductComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OpportunityEditProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
