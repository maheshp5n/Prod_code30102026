import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtCreateBoadingLodginDaComponent } from './bt-create-boading-lodgin-da.component';

describe('BtCreateBoadingLodginDaComponent', () => {
  let component: BtCreateBoadingLodginDaComponent;
  let fixture: ComponentFixture<BtCreateBoadingLodginDaComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateBoadingLodginDaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateBoadingLodginDaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
