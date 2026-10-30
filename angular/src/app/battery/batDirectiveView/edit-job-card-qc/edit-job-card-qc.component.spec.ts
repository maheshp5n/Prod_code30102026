import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditJobCardQcComponent } from './edit-job-card-qc.component';

describe('EditJobCardQcComponent', () => {
  let component: EditJobCardQcComponent;
  let fixture: ComponentFixture<EditJobCardQcComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditJobCardQcComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditJobCardQcComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
