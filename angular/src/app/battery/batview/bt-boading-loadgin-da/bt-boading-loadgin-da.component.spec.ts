import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtBoadingLoadginDaComponent } from './bt-boading-loadgin-da.component';

describe('BtBoadingLoadginDaComponent', () => {
  let component: BtBoadingLoadginDaComponent;
  let fixture: ComponentFixture<BtBoadingLoadginDaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtBoadingLoadginDaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtBoadingLoadginDaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
