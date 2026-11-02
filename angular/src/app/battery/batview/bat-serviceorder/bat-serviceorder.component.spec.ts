import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatServiceorderComponent } from './bat-serviceorder.component';

describe('BatServiceorderComponent', () => {
  let component: BatServiceorderComponent;
  let fixture: ComponentFixture<BatServiceorderComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatServiceorderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatServiceorderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
