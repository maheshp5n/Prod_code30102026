import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatSiteComponent } from './bat-site.component';

describe('BatSiteComponent', () => {
  let component: BatSiteComponent;
  let fixture: ComponentFixture<BatSiteComponent>;

  beforeEach(async(() => {
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
