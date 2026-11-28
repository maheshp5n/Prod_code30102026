import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatMslrequestDetailComponent } from './bat-mslrequest-detail.component';

describe('BatMslrequestDetailComponent', () => {
  let component: BatMslrequestDetailComponent;
  let fixture: ComponentFixture<BatMslrequestDetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatMslrequestDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatMslrequestDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
