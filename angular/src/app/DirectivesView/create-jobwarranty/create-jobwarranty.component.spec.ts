import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateJobwarrantyComponent } from './create-jobwarranty.component';

describe('CreateJobwarrantyComponent', () => {
  let component: CreateJobwarrantyComponent;
  let fixture: ComponentFixture<CreateJobwarrantyComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateJobwarrantyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateJobwarrantyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
