import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BtCreatePodComponent } from './bt-create-pod.component';

describe('BtCreatePodComponent', () => {
  let component: BtCreatePodComponent;
  let fixture: ComponentFixture<BtCreatePodComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BtCreatePodComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BtCreatePodComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
