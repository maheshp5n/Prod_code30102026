import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { JobwarrantydetailsComponent } from './jobwarrantydetails.component';

describe('JobwarrantydetailsComponent', () => {
  let component: JobwarrantydetailsComponent;
  let fixture: ComponentFixture<JobwarrantydetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ JobwarrantydetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JobwarrantydetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
