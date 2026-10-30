import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagecomponentComponent } from './managecomponent.component';

describe('ManagecomponentComponent', () => {
  let component: ManagecomponentComponent;
  let fixture: ComponentFixture<ManagecomponentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManagecomponentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagecomponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
