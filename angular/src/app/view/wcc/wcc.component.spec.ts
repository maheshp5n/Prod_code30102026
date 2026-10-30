import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WccComponent } from './wcc.component';

describe('WccComponent', () => {
  let component: WccComponent;
  let fixture: ComponentFixture<WccComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WccComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WccComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
