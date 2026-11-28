import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ManagecomponentindetailsComponent } from './managecomponentindetails.component';

describe('ManagecomponentindetailsComponent', () => {
  let component: ManagecomponentindetailsComponent;
  let fixture: ComponentFixture<ManagecomponentindetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ManagecomponentindetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManagecomponentindetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
