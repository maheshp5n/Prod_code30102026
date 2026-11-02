import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatUserroleComponent } from './bat-userrole.component';

describe('BatUserroleComponent', () => {
  let component: BatUserroleComponent;
  let fixture: ComponentFixture<BatUserroleComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatUserroleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatUserroleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
