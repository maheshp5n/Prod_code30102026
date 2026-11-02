import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ProutLinItmComponent } from './prout-lin-itm.component';

describe('ProutLinItmComponent', () => {
  let component: ProutLinItmComponent;
  let fixture: ComponentFixture<ProutLinItmComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ProutLinItmComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProutLinItmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
