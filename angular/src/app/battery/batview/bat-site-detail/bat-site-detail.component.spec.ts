import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatSiteDetailComponent } from './bat-site-detail.component';

describe('BatSiteDetailComponent', () => {
  let component: BatSiteDetailComponent;
  let fixture: ComponentFixture<BatSiteDetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatSiteDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatSiteDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
