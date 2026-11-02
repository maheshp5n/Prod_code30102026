import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { UpdateSapNumberComponent } from './update-sap-number.component';

describe('UpdateSapNumberComponent', () => {
  let component: UpdateSapNumberComponent;
  let fixture: ComponentFixture<UpdateSapNumberComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdateSapNumberComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateSapNumberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
