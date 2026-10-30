import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatNewServiceorderComponent } from './bat-new-serviceorder.component';

describe('BatNewServiceorderComponent', () => {
  let component: BatNewServiceorderComponent;
  let fixture: ComponentFixture<BatNewServiceorderComponent>;

  beforeEach(async(() => {
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
