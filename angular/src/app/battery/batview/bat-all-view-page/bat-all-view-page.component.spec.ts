import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatAllViewPageComponent } from './bat-all-view-page.component';

describe('BatAllViewPageComponent', () => {
  let component: BatAllViewPageComponent;
  let fixture: ComponentFixture<BatAllViewPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BatAllViewPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatAllViewPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
