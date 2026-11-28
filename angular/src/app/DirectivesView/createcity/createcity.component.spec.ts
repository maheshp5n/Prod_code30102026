import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CreatecityComponent } from './createcity.component';

describe('CreatecityComponent', () => {
  let component: CreatecityComponent;
  let fixture: ComponentFixture<CreatecityComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ CreatecityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatecityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
