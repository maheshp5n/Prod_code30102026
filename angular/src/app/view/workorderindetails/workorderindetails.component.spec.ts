import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { WorkorderindetailsComponent } from './workorderindetails.component';

describe('WorkorderindetailsComponent', () => {
  let component: WorkorderindetailsComponent;
  let fixture: ComponentFixture<WorkorderindetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkorderindetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkorderindetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
