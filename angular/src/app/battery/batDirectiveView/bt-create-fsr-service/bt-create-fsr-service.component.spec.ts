import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtCreateFsrServiceComponent } from './bt-create-fsr-service.component';

describe('BtCreateFsrServiceComponent', () => {
  let component: BtCreateFsrServiceComponent;
  let fixture: ComponentFixture<BtCreateFsrServiceComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateFsrServiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateFsrServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
