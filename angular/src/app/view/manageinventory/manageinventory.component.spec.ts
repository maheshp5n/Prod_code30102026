import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { ManageinventoryComponent } from './manageinventory.component';

describe('ManageinventoryComponent', () => {
  let component: ManageinventoryComponent;
  let fixture: ComponentFixture<ManageinventoryComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ManageinventoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageinventoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
