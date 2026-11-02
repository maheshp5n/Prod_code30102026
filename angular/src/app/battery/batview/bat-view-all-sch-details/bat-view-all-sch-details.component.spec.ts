import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatViewAllSchDetailsComponent } from './bat-view-all-sch-details.component';

describe('BatViewAllSchDetailsComponent', () => {
  let component: BatViewAllSchDetailsComponent;
  let fixture: ComponentFixture<BatViewAllSchDetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatViewAllSchDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatViewAllSchDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
