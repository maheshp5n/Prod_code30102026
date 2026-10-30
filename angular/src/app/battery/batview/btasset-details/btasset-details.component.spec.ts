import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BTassetDetailsComponent } from './btasset-details.component';

describe('BTassetDetailsComponent', () => {
  let component: BTassetDetailsComponent;
  let fixture: ComponentFixture<BTassetDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BTassetDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BTassetDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
