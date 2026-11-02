import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateNewOpportunityComponent } from './create-new-opportunity.component';

describe('CreateNewOpportunityComponent', () => {
  let component: CreateNewOpportunityComponent;
  let fixture: ComponentFixture<CreateNewOpportunityComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewOpportunityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewOpportunityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
