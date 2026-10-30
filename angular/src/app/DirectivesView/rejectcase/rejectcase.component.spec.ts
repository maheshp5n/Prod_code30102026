import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RejectcaseComponent } from './rejectcase.component';

describe('RejectcaseComponent', () => {
  let component: RejectcaseComponent;
  let fixture: ComponentFixture<RejectcaseComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RejectcaseComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RejectcaseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
