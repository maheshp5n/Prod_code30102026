import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ServicereportComponent } from './servicereport.component';

describe('ServicereportComponent', () => {
  let component: ServicereportComponent;
  let fixture: ComponentFixture<ServicereportComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ServicereportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ServicereportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
