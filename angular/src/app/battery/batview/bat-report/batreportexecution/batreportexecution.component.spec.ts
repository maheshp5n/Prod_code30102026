import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatreportexecutionComponent } from './batreportexecution.component';

describe('BatreportexecutionComponent', () => {
  let component: BatreportexecutionComponent;
  let fixture: ComponentFixture<BatreportexecutionComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatreportexecutionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatreportexecutionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
