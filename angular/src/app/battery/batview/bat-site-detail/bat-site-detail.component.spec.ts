import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatSiteDetailComponent } from './bat-site-detail.component';

describe('BatSiteDetailComponent', () => {
  let component: BatSiteDetailComponent;
  let fixture: ComponentFixture<BatSiteDetailComponent>;

  beforeEach(async(() => {
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
