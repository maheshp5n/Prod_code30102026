import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkOrderForQueteComponent } from './work-order-for-quete.component';

describe('WorkOrderForQueteComponent', () => {
  let component: WorkOrderForQueteComponent;
  let fixture: ComponentFixture<WorkOrderForQueteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkOrderForQueteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkOrderForQueteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
