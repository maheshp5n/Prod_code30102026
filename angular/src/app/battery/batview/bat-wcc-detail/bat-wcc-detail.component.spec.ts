import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatWccDetailComponent } from './bat-wcc-detail.component';

describe('BatWccDetailComponent', () => {
  let component: BatWccDetailComponent;
  let fixture: ComponentFixture<BatWccDetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatWccDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatWccDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
