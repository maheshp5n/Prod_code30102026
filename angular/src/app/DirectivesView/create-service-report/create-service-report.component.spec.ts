import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateServiceReportComponent } from './create-service-report.component';

describe('CreateServiceReportComponent', () => {
  let component: CreateServiceReportComponent;
  let fixture: ComponentFixture<CreateServiceReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateServiceReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateServiceReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
