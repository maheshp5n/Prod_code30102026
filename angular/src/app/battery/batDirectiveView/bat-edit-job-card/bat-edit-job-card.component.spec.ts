import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatEditJobCardComponent } from './bat-edit-job-card.component';

describe('BatEditJobCardComponent', () => {
  let component: BatEditJobCardComponent;
  let fixture: ComponentFixture<BatEditJobCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatEditJobCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatEditJobCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
