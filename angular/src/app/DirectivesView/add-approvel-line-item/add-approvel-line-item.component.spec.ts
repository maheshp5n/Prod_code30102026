import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddApprovelLineItemComponent } from './add-approvel-line-item.component';

describe('AddApprovelLineItemComponent', () => {
  let component: AddApprovelLineItemComponent;
  let fixture: ComponentFixture<AddApprovelLineItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddApprovelLineItemComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddApprovelLineItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
