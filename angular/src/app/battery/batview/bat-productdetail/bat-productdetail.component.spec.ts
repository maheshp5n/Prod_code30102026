import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BatProductdetailComponent } from './bat-productdetail.component';

describe('BatProductdetailComponent', () => {
  let component: BatProductdetailComponent;
  let fixture: ComponentFixture<BatProductdetailComponent>;

  beforeEach(async(() => {
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
