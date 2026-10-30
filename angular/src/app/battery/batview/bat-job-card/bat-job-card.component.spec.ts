import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatJobCardComponent } from './bat-job-card.component';

describe('BatJobCardComponent', () => {
  let component: BatJobCardComponent;
  let fixture: ComponentFixture<BatJobCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatJobCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatJobCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
