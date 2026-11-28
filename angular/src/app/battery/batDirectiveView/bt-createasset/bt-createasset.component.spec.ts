import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtCreateassetComponent } from './bt-createasset.component';

describe('BtCreateassetComponent', () => {
  let component: BtCreateassetComponent;
  let fixture: ComponentFixture<BtCreateassetComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreateassetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreateassetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
