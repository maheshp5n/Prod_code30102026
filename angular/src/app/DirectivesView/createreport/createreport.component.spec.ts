import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreatereportComponent } from './createreport.component';

describe('CreatereportComponent', () => {
  let component: CreatereportComponent;
  let fixture: ComponentFixture<CreatereportComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreatereportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatereportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
