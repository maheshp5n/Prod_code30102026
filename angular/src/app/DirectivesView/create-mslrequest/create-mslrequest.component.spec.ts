import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMslrequestComponent } from './create-mslrequest.component';

describe('CreateMslrequestComponent', () => {
  let component: CreateMslrequestComponent;
  let fixture: ComponentFixture<CreateMslrequestComponent>;

  beforeEach(async(() => {
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
