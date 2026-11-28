import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatProductdetailComponent } from './bat-productdetail.component';

describe('BatProductdetailComponent', () => {
  let component: BatProductdetailComponent;
  let fixture: ComponentFixture<BatProductdetailComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatProductdetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatProductdetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
