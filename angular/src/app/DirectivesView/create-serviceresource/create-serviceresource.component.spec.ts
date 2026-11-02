import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreateServiceresourceComponent } from './create-serviceresource.component';

describe('CreateServiceresourceComponent', () => {
  let component: CreateServiceresourceComponent;
  let fixture: ComponentFixture<CreateServiceresourceComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateServiceresourceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateServiceresourceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
