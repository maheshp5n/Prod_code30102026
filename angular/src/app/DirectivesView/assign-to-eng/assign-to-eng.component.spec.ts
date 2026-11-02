import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AssignToEngComponent } from './assign-to-eng.component';

describe('AssignToEngComponent', () => {
  let component: AssignToEngComponent;
  let fixture: ComponentFixture<AssignToEngComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AssignToEngComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignToEngComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
