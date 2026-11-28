import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtNewproductrequestdirectiveComponent } from './bt-newproductrequestdirective.component';

describe('BtNewproductrequestdirectiveComponent', () => {
  let component: BtNewproductrequestdirectiveComponent;
  let fixture: ComponentFixture<BtNewproductrequestdirectiveComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtNewproductrequestdirectiveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtNewproductrequestdirectiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
