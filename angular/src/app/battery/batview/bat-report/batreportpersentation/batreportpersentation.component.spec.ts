import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatreportpersentationComponent } from './batreportpersentation.component';

describe('BatreportpersentationComponent', () => {
  let component: BatreportpersentationComponent;
  let fixture: ComponentFixture<BatreportpersentationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatreportpersentationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatreportpersentationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
