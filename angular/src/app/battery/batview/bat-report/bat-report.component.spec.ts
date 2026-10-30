import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatReportComponent } from './bat-report.component';

describe('BatReportComponent', () => {
  let component: BatReportComponent;
  let fixture: ComponentFixture<BatReportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatReportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
