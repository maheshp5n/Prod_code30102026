import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatUserroleComponent } from './bat-userrole.component';

describe('BatUserroleComponent', () => {
  let component: BatUserroleComponent;
  let fixture: ComponentFixture<BatUserroleComponent>;

  beforeEach(async(() => {
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
