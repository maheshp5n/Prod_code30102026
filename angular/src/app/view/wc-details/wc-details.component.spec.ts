import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WcDetailsComponent } from './wc-details.component';

describe('WcDetailsComponent', () => {
  let component: WcDetailsComponent;
  let fixture: ComponentFixture<WcDetailsComponent>;

  beforeEach(async(() => {
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
