import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PrcactionComponent } from './prcaction.component';

describe('PrcactionComponent', () => {
  let component: PrcactionComponent;
  let fixture: ComponentFixture<PrcactionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PrcactionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PrcactionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
