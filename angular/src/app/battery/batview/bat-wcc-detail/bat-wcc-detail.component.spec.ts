import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatWccDetailComponent } from './bat-wcc-detail.component';

describe('BatWccDetailComponent', () => {
  let component: BatWccDetailComponent;
  let fixture: ComponentFixture<BatWccDetailComponent>;

  beforeEach(async(() => {
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
