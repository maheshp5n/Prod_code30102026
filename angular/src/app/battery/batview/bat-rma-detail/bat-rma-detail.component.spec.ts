import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatRmaDetailComponent } from './bat-rma-detail.component';

describe('BatRmaDetailComponent', () => {
  let component: BatRmaDetailComponent;
  let fixture: ComponentFixture<BatRmaDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatRmaDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatRmaDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
