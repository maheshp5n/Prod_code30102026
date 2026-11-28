import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SsadetailsComponent } from './ssadetails.component';

describe('SsadetailsComponent', () => {
  let component: SsadetailsComponent;
  let fixture: ComponentFixture<SsadetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ SsadetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SsadetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
