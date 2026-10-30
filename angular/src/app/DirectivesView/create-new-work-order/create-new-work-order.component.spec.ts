import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNewWorkOrderComponent } from './create-new-work-order.component';

describe('CreateNewWorkOrderComponent', () => {
  let component: CreateNewWorkOrderComponent;
  let fixture: ComponentFixture<CreateNewWorkOrderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewWorkOrderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewWorkOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
