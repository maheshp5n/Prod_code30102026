import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { UserprofileDetailsComponent } from './userprofile-details.component';

describe('UserprofileDetailsComponent', () => {
  let component: UserprofileDetailsComponent;
  let fixture: ComponentFixture<UserprofileDetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ UserprofileDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserprofileDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
