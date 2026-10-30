import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BordingDaComponent } from './bording-da.component';

describe('BordingDaComponent', () => {
  let component: BordingDaComponent;
  let fixture: ComponentFixture<BordingDaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BordingDaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BordingDaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
