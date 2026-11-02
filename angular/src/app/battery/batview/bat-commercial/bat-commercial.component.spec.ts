import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatCommercialComponent } from './bat-commercial.component';

describe('BatCommercialComponent', () => {
  let component: BatCommercialComponent;
  let fixture: ComponentFixture<BatCommercialComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatCommercialComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatCommercialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
