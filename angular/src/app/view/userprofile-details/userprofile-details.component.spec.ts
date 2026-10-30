import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserprofileDetailsComponent } from './userprofile-details.component';

describe('UserprofileDetailsComponent', () => {
  let component: UserprofileDetailsComponent;
  let fixture: ComponentFixture<UserprofileDetailsComponent>;

  beforeEach(async(() => {
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
