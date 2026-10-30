import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommercialPageDetailComponent } from './commercial-page-detail.component';

describe('CommercialPageDetailComponent', () => {
  let component: CommercialPageDetailComponent;
  let fixture: ComponentFixture<CommercialPageDetailComponent>;

  beforeEach(async(() => {
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
