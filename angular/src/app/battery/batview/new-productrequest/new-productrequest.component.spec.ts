import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { NewProductrequestComponent } from './new-productrequest.component';

describe('NewProductrequestComponent', () => {
  let component: NewProductrequestComponent;
  let fixture: ComponentFixture<NewProductrequestComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ NewProductrequestComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewProductrequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
