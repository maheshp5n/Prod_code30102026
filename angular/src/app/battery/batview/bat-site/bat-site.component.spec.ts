import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatSiteComponent } from './bat-site.component';

describe('BatSiteComponent', () => {
  let component: BatSiteComponent;
  let fixture: ComponentFixture<BatSiteComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatSiteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatSiteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
