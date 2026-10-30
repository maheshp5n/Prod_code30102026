import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BtActionHistoryComponent } from './bt-action-history.component';

describe('BtActionHistoryComponent', () => {
  let component: BtActionHistoryComponent;
  let fixture: ComponentFixture<BtActionHistoryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BtActionHistoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtActionHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
