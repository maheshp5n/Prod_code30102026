import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ServicereportindetailsComponent } from './servicereportindetails.component';

describe('ServicereportindetailsComponent', () => {
  let component: ServicereportindetailsComponent;
  let fixture: ComponentFixture<ServicereportindetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ServicereportindetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServicereportindetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
