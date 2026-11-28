import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatCaseComponent } from './bat-case.component';

describe('BatCaseComponent', () => {
  let component: BatCaseComponent;
  let fixture: ComponentFixture<BatCaseComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatCaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatCaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
