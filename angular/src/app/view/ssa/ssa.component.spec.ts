import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SsaComponent } from './ssa.component';

describe('SsaComponent', () => {
  let component: SsaComponent;
  let fixture: ComponentFixture<SsaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SsaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SsaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
