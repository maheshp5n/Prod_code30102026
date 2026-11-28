import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateMslrequestComponent } from './create-mslrequest.component';

describe('CreateMslrequestComponent', () => {
  let component: CreateMslrequestComponent;
  let fixture: ComponentFixture<CreateMslrequestComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateMslrequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateMslrequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
