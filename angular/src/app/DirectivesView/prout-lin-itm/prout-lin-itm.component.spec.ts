import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProutLinItmComponent } from './prout-lin-itm.component';

describe('ProutLinItmComponent', () => {
  let component: ProutLinItmComponent;
  let fixture: ComponentFixture<ProutLinItmComponent>;

  beforeEach(async(() => {
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
