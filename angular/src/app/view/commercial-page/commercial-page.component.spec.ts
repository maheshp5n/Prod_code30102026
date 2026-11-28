import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CommercialPageComponent } from './commercial-page.component';

describe('CommercialPageComponent', () => {
  let component: CommercialPageComponent;
  let fixture: ComponentFixture<CommercialPageComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CommercialPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommercialPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
