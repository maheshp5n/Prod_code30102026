import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatFsrServiceComponent } from './bat-fsr-service.component';

describe('BatFsrServiceComponent', () => {
  let component: BatFsrServiceComponent;
  let fixture: ComponentFixture<BatFsrServiceComponent>;

  beforeEach(async(() => {
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
