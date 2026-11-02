import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatNewServiceorderComponent } from './bat-new-serviceorder.component';

describe('BatNewServiceorderComponent', () => {
  let component: BatNewServiceorderComponent;
  let fixture: ComponentFixture<BatNewServiceorderComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatNewServiceorderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatNewServiceorderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
