import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BordingDaComponent } from './bording-da.component';

describe('BordingDaComponent', () => {
  let component: BordingDaComponent;
  let fixture: ComponentFixture<BordingDaComponent>;

  beforeEach(waitForAsync(() => {
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
