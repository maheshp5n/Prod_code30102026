import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { WcDetailsComponent } from './wc-details.component';

describe('WcDetailsComponent', () => {
  let component: WcDetailsComponent;
  let fixture: ComponentFixture<WcDetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ WcDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WcDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
