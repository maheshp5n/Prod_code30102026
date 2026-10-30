import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JobwarrantyComponent } from './jobwarranty.component';

describe('JobwarrantyComponent', () => {
  let component: JobwarrantyComponent;
  let fixture: ComponentFixture<JobwarrantyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JobwarrantyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JobwarrantyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
