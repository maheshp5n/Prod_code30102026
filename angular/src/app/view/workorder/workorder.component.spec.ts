import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { WorkorderComponent } from './workorder.component';

describe('WorkorderComponent', () => {
  let component: WorkorderComponent;
  let fixture: ComponentFixture<WorkorderComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkorderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkorderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
