import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignToEngComponent } from './assign-to-eng.component';

describe('AssignToEngComponent', () => {
  let component: AssignToEngComponent;
  let fixture: ComponentFixture<AssignToEngComponent>;

  beforeEach(async(() => {
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
