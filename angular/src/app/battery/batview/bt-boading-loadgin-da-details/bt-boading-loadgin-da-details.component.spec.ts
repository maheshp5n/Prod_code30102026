import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtBoadingLoadginDaDetailsComponent } from './bt-boading-loadgin-da-details.component';

describe('BtBoadingLoadginDaDetailsComponent', () => {
  let component: BtBoadingLoadginDaDetailsComponent;
  let fixture: ComponentFixture<BtBoadingLoadginDaDetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtBoadingLoadginDaDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtBoadingLoadginDaDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
