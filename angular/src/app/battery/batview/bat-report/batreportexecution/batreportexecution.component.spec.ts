import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatreportexecutionComponent } from './batreportexecution.component';

describe('BatreportexecutionComponent', () => {
  let component: BatreportexecutionComponent;
  let fixture: ComponentFixture<BatreportexecutionComponent>;

  beforeEach(async(() => {
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
