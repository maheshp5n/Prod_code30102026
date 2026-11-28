import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BatServiceresourceDetailsComponent } from './bat-serviceresource-details.component';

describe('BatServiceresourceDetailsComponent', () => {
  let component: BatServiceresourceDetailsComponent;
  let fixture: ComponentFixture<BatServiceresourceDetailsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BatServiceresourceDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BatServiceresourceDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
