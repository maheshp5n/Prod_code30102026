import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAnnexureLineItemComponent } from './create-annexureLineItem.component';

describe('CreateAnnexureLineItemComponent', () => {
  let component: CreateAnnexureLineItemComponent;
  let fixture: ComponentFixture<CreateAnnexureLineItemComponent>;

  beforeEach(async(() => {
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
