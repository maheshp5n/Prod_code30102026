import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatMyInventoryComponent } from './bat-my-inventory.component';

describe('BatMyInventoryComponent', () => {
  let component: BatMyInventoryComponent;
  let fixture: ComponentFixture<BatMyInventoryComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatMyInventoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatMyInventoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
