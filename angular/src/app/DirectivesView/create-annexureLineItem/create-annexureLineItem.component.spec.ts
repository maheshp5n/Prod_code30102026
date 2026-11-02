import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateAnnexureLineItemComponent } from './create-annexureLineItem.component';

describe('CreateAnnexureLineItemComponent', () => {
  let component: CreateAnnexureLineItemComponent;
  let fixture: ComponentFixture<CreateAnnexureLineItemComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [CreateAnnexureLineItemComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateAnnexureLineItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
