import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatWccComponent } from './bat-wcc.component';

describe('BatWccComponent', () => {
  let component: BatWccComponent;
  let fixture: ComponentFixture<BatWccComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatWccComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatWccComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
