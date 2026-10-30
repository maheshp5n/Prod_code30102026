import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatServiceorderComponent } from './bat-serviceorder.component';

describe('BatServiceorderComponent', () => {
  let component: BatServiceorderComponent;
  let fixture: ComponentFixture<BatServiceorderComponent>;

  beforeEach(async(() => {
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
