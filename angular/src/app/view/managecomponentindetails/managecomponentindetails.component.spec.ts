import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagecomponentindetailsComponent } from './managecomponentindetails.component';

describe('ManagecomponentindetailsComponent', () => {
  let component: ManagecomponentindetailsComponent;
  let fixture: ComponentFixture<ManagecomponentindetailsComponent>;

  beforeEach(async(() => {
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
