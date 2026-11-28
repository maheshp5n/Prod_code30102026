import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateManagecomponentComponent } from './create-managecomponent.component';

describe('CreateManagecomponentComponent', () => {
  let component: CreateManagecomponentComponent;
  let fixture: ComponentFixture<CreateManagecomponentComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateManagecomponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateManagecomponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
