import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CommercialPageDetailComponent } from './commercial-page-detail.component';

describe('CommercialPageDetailComponent', () => {
  let component: CommercialPageDetailComponent;
  let fixture: ComponentFixture<CommercialPageDetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CommercialPageDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommercialPageDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
