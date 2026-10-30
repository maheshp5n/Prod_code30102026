import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatHelpguideComponent } from './bat-helpguide.component';

describe('BatHelpguideComponent', () => {
  let component: BatHelpguideComponent;
  let fixture: ComponentFixture<BatHelpguideComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatHelpguideComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatHelpguideComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
