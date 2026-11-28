import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateManageinventoryComponent } from './create-manageinventory.component';

describe('CreateManageinventoryComponent', () => {
  let component: CreateManageinventoryComponent;
  let fixture: ComponentFixture<CreateManageinventoryComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateManageinventoryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateManageinventoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
