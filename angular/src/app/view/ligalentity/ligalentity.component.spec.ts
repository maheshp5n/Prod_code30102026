import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LigalentityComponent } from './ligalentity.component';

describe('LigalentityComponent', () => {
  let component: LigalentityComponent;
  let fixture: ComponentFixture<LigalentityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LigalentityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LigalentityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
