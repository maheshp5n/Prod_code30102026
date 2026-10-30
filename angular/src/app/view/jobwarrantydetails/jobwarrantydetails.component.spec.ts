import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JobwarrantydetailsComponent } from './jobwarrantydetails.component';

describe('JobwarrantydetailsComponent', () => {
  let component: JobwarrantydetailsComponent;
  let fixture: ComponentFixture<JobwarrantydetailsComponent>;

  beforeEach(async(() => {
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
