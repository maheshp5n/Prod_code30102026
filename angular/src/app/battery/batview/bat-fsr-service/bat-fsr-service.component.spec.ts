import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatFsrServiceComponent } from './bat-fsr-service.component';

describe('BatFsrServiceComponent', () => {
  let component: BatFsrServiceComponent;
  let fixture: ComponentFixture<BatFsrServiceComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatFsrServiceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatFsrServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
