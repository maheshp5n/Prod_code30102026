import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatRmaComponent } from './bat-rma.component';

describe('BatRmaComponent', () => {
  let component: BatRmaComponent;
  let fixture: ComponentFixture<BatRmaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatRmaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatRmaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
