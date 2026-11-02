import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtCreatesiteComponent } from './bt-createsite.component';

describe('BtCreatesiteComponent', () => {
  let component: BtCreatesiteComponent;
  let fixture: ComponentFixture<BtCreatesiteComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreatesiteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreatesiteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
